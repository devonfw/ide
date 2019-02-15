package com.devonfw.ide.configurator.resolve;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.devonfw.ide.configurator.logging.Log;

/**
 * Implementation of {@link VariableResolver}.
 *
 * @since 3.0.0
 */
public class VariableResolverImpl implements VariableResolver {

  private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

  private static final String VARIABLE_PREFIX = "${";

  private static final String VARIABLE_SUFFIX = "}";

  private final Properties variables;

  private Properties altVariables;

  /**
   * The constructor.
   *
   * @param variables the {@link Properties} with the variables to resolve.
   */
  public VariableResolverImpl(Properties variables) {

    this.variables = variables;
  }

  /**
   * @param path a (potential) {@link Path}
   * @return the given path without trailing slash.
   */
  public static String normalizePath(String path) {

    if (path.endsWith("/") || path.endsWith("\\")) {
      return path.substring(0, path.length() - 1);
    }
    return path;
  }

  private Properties getAltVariables() {

    if (this.altVariables == null) {
      this.altVariables = new Properties();
      for (Object keyObject : this.variables.keySet()) {
        String key = keyObject.toString();
        String value = this.variables.getProperty(key);
        Path path = Paths.get(value);
        Log.LOGGER.fine("Checking if variable points to symlink: " + path);
        if (Files.isSymbolicLink(path)) {
          try {
            Path resolved = Files.readSymbolicLink(path);
            String altValue = normalizePath(resolved.toString());
            Log.LOGGER.fine("Symlink resolved to: " + altValue);
            if (!altValue.equals(value)) {
              this.altVariables.put(key, altValue);
            }
          } catch (IOException e) {
            Log.LOGGER.finest(e.getMessage());
          }
        }
      }
    }
    return this.altVariables;
  }

  @Override
  public String resolve(String text) {

    Matcher m = VARIABLE_PATTERN.matcher(text);
    String result = text;
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String match = m.group(1);
      String replacement = resolveVariable(match);
      if (replacement != null) {
        m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
      }
    }
    m.appendTail(sb);
    result = sb.toString();
    return result;
  }

  /**
   * @param name the name of the variable to resolve.
   * @return the value of the variable with the given {@code name}.
   */
  protected String resolveVariable(String name) {

    String result = null;
    Object value = this.variables.get(name);
    if (value != null) {
      result = value.toString();
    } else {
      result = System.getProperty(name);
      if (result == null) {
        result = System.getenv(name);
      }
    }
    return result;
  }

  @Override
  public String inverseResolve(String text) {

    String result = text;
    for (Map.Entry<Object, Object> entry : this.variables.entrySet()) {
      result =
          result.replace(entry.getValue().toString(), VARIABLE_PREFIX + entry.getKey().toString() + VARIABLE_SUFFIX);
    }
    for (Map.Entry<Object, Object> entry : getAltVariables().entrySet()) {
      result =
          result.replace(entry.getValue().toString(), VARIABLE_PREFIX + entry.getKey().toString() + VARIABLE_SUFFIX);
    }
    if (!result.equals(text)) {
      Log.LOGGER.fine("Inverse resolved '" + text + "' to '" + result + "'.");
    }
    return result;
  }

}
