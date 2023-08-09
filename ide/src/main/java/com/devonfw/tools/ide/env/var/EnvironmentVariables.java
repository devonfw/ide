package com.devonfw.tools.ide.env.var;

import java.nio.file.Path;

import com.devonfw.tools.ide.log.IdeLogger;

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
   * @return the value of the variable with the given {@code name} without {@link #getParent() inheritance from parent}.
   *         Will be {@code null} if no such variable is defined.
   */
  String getFlat(String name);

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
