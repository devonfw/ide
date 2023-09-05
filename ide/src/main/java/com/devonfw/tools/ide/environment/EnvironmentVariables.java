package com.devonfw.tools.ide.environment;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Locale;

import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.version.VersionIdentifier;

/**
 * Interface for the environment with the variables.
 */
public interface EnvironmentVariables {

  /** Filename of the default variable configuration file. {@value} */
  String DEFAULT_PROPERTIES = "ide.properties";

  /** Filename of the legacy variable configuration file. {@value} */
  String LEGACY_PROPERTIES = "devon.properties";

  /**
   * @param name the name of the environment variable to get.
   * @return the value of the variable with the given {@code name}. Will be {@code null} if no such variable is defined.
   */
  default String get(String name) {

    String value = getFlat(name);
    if (value == null) {
      EnvironmentVariables parent = getParent();
      if (parent != null) {
        value = parent.get(name);
      }
    }
    return value;
  }

  /**
   * @param name the name of the environment variable to get.
   * @return the value of the variable with the given {@code name} as {@link Path}. Will be {@code null} if no such
   *         variable is defined.
   */
  default Path getPath(String name) {

    String value = get(name);
    if (value == null) {
      return null;
    }
    return Paths.get(value);
  }

  /**
   * @param name the name of the environment variable to get.
   * @return the value of the variable with the given {@code name} without {@link #getParent() inheritance from parent}.
   *         Will be {@code null} if no such variable is defined.
   */
  String getFlat(String name);

  /**
   * @param tool the name of the tool (e.g. "java").
   * @return the edition of the tool to use.
   */
  default String getToolEdition(String tool) {

    String variable = tool.toUpperCase(Locale.ROOT) + "_EDITION";
    String value = get(variable);
    if (value == null) {
      value = tool;
    }
    return value;
  }

  /**
   * @param tool the name of the tool (e.g. "java").
   * @return the {@link VersionIdentifier} with the version of the tool to use. Will be
   *         {@link VersionIdentifier#LATEST} if undefined.
   */
  default VersionIdentifier getToolVersion(String tool) {

    String variable = getToolVersionVariable(tool);
    String value = get(variable);
    if (value == null) {
      return VersionIdentifier.LATEST;
    }
    return VersionIdentifier.of(value);
  }

  /**
   * @return the {@link EnvironmentVariablesType type} of this {@link EnvironmentVariables}.
   */
  EnvironmentVariablesType getType();

  /**
   * @param type the {@link #getType() type} of the requested {@link EnvironmentVariables}.
   * @return the {@link EnvironmentVariables} with the given {@link #getType() type} from this
   *         {@link EnvironmentVariables} along the {@link #getParent() parent} hierarchy or {@code null} if not found.
   */
  default EnvironmentVariables getByType(EnvironmentVariablesType type) {

    if (type == getType()) {
      return this;
    }
    EnvironmentVariables parent = getParent();
    if (parent == null) {
      return null;
    } else {
      return parent.getByType(type);
    }
  }

  /**
   * @return the {@link Path} to the underlying properties file or {@code null} if not based on such file (e.g. for EVS
   *         or {@link EnvironmentVariablesResolved}).
   */
  Path getPropertiesFilePath();

  /**
   * @return the source identifier describing this {@link EnvironmentVariables} for debugging.
   */
  String getSource();

  /**
   * @return the parent {@link EnvironmentVariables} to inherit from or {@code null} if this is the
   *         {@link EnvironmentVariablesType#SYSTEM root} {@link EnvironmentVariables} instance.
   */
  default EnvironmentVariables getParent() {

    return null;
  }

  /**
   * @param name the {@link com.devonfw.tools.ide.variable.VariableDefinition#getName() name} of the variable to set.
   * @param value the new {@link #get(String) value} of the variable to set. May be {@code null} to unset the variable.
   * @param export - {@code true} if the variable needs to be exported, {@code false} otherwise.
   * @return the old variable value.
   */
  default String set(String name, String value, boolean export) {

    throw new UnsupportedOperationException();
  }

  /**
   * Saves any potential {@link #set(String, String, boolean) changes} of this {@link EnvironmentVariables}.
   */
  default void save() {

  }

  /**
   * @param name the {@link com.devonfw.tools.ide.variable.VariableDefinition#getName() name} of the variable to
   *        search for.
   * @return the closest {@link EnvironmentVariables} instance that defines the variable with the given {@code name} or
   *         {@code null} if the variable is not defined.
   */
  default EnvironmentVariables findVariable(String name) {

    String value = getFlat(name);
    if (value != null) {
      return this;
    }
    EnvironmentVariables parent = getParent();
    if (parent == null) {
      return null;
    } else {
      return parent.findVariable(name);
    }
  }

  /**
   * @return the {@link Collection} of the {@link VariableLine}s defined by this {@link EnvironmentVariables} including
   *         inheritance.
   */
  Collection<VariableLine> collectVariables();

  /**
   * @param logger the {@link IdeLogger}.
   * @return the system {@link EnvironmentVariables} building the root of the {@link EnvironmentVariables} hierarchy.
   */
  static AbstractEnvironmentVariables ofSystem(IdeLogger logger) {

    return EnvironmentVariablesSystem.of(logger);
  }

  /**
   * @param tool the name of the tool.
   * @return the name of the version variable.
   */
  static String getToolVersionVariable(String tool) {

    return tool.toUpperCase(Locale.ROOT) + "_VERSION";
  }

}
