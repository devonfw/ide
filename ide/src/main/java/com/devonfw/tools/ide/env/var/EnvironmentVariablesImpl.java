package com.devonfw.tools.ide.env.var;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Implementation of {@link EnvironmentVariables}.
 */
class EnvironmentVariablesImpl extends AbstractEnvironmentVariables {

  /** Filename of the default variable configuration file. {@value} */
  private static final String IDE_PROPERTIES = "ide.properties";

  /** Filename of the legacy variable configuration file. {@value} */
  private static final String DEVON_PROPERTIES = "devon.properties";

  private final Map<String, String> variables;

  /**
   * The constructor.
   *
   * @param parent the parent {@link EnvironmentVariables} to inherit from.
   * @param source the {@link #getSource() source}.
   * @param logger the {@link IdeLogger}.
   * @param variables the underlying variables.
   */
  EnvironmentVariablesImpl(EnvironmentVariables parent, String source, IdeLogger logger,
      Map<String, String> variables) {

    super(parent, source, logger);
    this.variables = variables;
  }

  @Override
  public String getFlat(String name) {

    String value = this.variables.get(name);
    if (value == null) {
      this.logger.trace("{}: Variable {} is undefined.", this.source, name);
    } else {
      this.logger.trace("{}: Variable {}={}", this.source, name, value);
    }
    return value;
  }

  @Override
  public String toString() {

    return this.source + ":\n" + this.variables;
  }

  /**
   * @param childSource the {@link #getSource() source} of the new child.
   * @param childVariables the {@link Map} with the {@link #getFlat(String)} variables} of the new child.
   * @return the new child {@link EnvironmentVariables}.
   */
  private EnvironmentVariablesImpl createChild(String childSource, Map<String, String> childVariables) {

    return new EnvironmentVariablesImpl(this, childSource, this.logger, childVariables);
  }

  @Override
  public EnvironmentVariablesImpl extend(Path propertiesPath) {

    if (!Files.isDirectory(propertiesPath)) {
      this.logger.debug("Directory {} does not exist.", propertiesPath);
      return this;
    }
    Path propertiesFile = propertiesPath.resolve(IDE_PROPERTIES);
    Map<String, String> childVariables = loadProperties(propertiesFile, false);
    if (childVariables == null) {
      propertiesFile = propertiesPath.resolve(DEVON_PROPERTIES);
      childVariables = loadProperties(propertiesFile, true);
      if (childVariables == null) {
        return this;
      }
    }
    return createChild(propertiesFile.toString(), childVariables);
  }

  private Map<String, String> loadProperties(Path propertiesFile, boolean legacy) {

    if (!Files.exists(propertiesFile)) {
      this.logger.trace("Properties not found at {}", propertiesFile);
      return null;
    }
    this.logger.trace("Loading properties from {}", propertiesFile);
    Properties properties = new Properties();
    try (BufferedReader reader = Files.newBufferedReader(propertiesFile)) {
      properties.load(reader);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load properties from " + propertiesFile, e);
    }
    Map<String, String> map = new HashMap<>();
    for (Entry<Object, Object> entry : properties.entrySet()) {
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();
      value = value.trim();
      if (key.startsWith("export ")) {
        key = key.substring(7); // remove leading export
      }
      int valueLength = value.length();
      if ((valueLength >= 2) && value.startsWith("\"") && value.endsWith("\"")) {
        // remove quotes
        value = value.substring(0, valueLength - 2);
      }
      String duplicate = map.put(key, value);
      if (duplicate != null) {
        this.logger.warning("Duplicate property '{}' mapped to '{}' and '{}' in {}", key, value, duplicate,
            propertiesFile);
      }
    }
    return map;
  }

}
