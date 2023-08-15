package com.devonfw.tools.ide.env.var;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.version.VersionIdentifier;

/**
 * Interface for the environment with the variables.
 */
public interface EnvironmentVariables {

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
   *         {@link VersionIdentifier#VERSION_LATEST} if undefined.
   */
  default VersionIdentifier getToolVersion(String tool) {

    String variable = tool.toUpperCase(Locale.ROOT) + "_VERSION";
    String value = get(variable);
    if (value == null) {
      return VersionIdentifier.VERSION_LATEST;
    }
    return VersionIdentifier.of(value);

  }

  /**
   * @return the unique source identifier of this {@link EnvironmentVariables}. E.g. the file-system path to the
   *         {@link java.util.Properties properties} file defining the variables.
   */
  String getSource();

  /**
   * @return the parent {@link EnvironmentVariables} to inherit from or {@code null} if this is the root
   *         {@link EnvironmentVariables} instance.
   */
  default EnvironmentVariables getParent() {

    return null;
  }

  /**
   * @param propertiesPath the directory where the properties file with the {@link #getFlat(String)} variables} of the
   *        new child is expected.
   * @return the new child {@link EnvironmentVariables} or this instance itself if no properties file was found in the
   *         given {@code propertiesPath}.
   */
  EnvironmentVariables extend(Path propertiesPath);

  /**
   * @return a new child {@link EnvironmentVariables} that will resolve variables recursively or this instance itself if
   *         already satisfied.
   */
  EnvironmentVariables resolved();

  /**
   * @param logger the {@link IdeLogger}.
   * @return the system {@link EnvironmentVariables} building the root of the {@link EnvironmentVariables} hierarchy.
   */
  static EnvironmentVariables ofSystem(IdeLogger logger) {

    return new EnvironmentVariablesImpl(null, "System", logger, System.getenv());
  }
}
