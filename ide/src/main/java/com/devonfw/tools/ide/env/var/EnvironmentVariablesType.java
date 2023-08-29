package com.devonfw.tools.ide.env.var;

import com.devonfw.tools.ide.env.Environment;

/**
 * The type of an instance of {@link EnvironmentVariables}.
 *
 * @see EnvironmentVariables#getType()
 */
public enum EnvironmentVariablesType {

  /** Type of {@link EnvironmentVariables} from the {@link System#getenv() system environment}. */
  SYSTEM,

  /** Type of {@link EnvironmentVariables} from the {@link Environment#getUserHome() users HOME directory}. */
  USER,

  /** Type of {@link EnvironmentVariables} from the {@link Environment#getSettingsPath() settings directory}. */
  SETTINGS,

  /** Type of {@link EnvironmentVariables} from the {@link Environment#getWorkspacePath() workspace directory}. */
  WORKSPACE,

  /**
   * Type of {@link EnvironmentVariables} from the {@link Environment#getConfPath() conf directory}. Allows the user to
   * override or customize project specific variables.
   */
  CONF,

  /**
   * Type of {@link EnvironmentVariables} that contains the logic to resolve variables from values with "${«variable»}"
   * syntax.
   *
   * @see EnvironmentVariablesResolved
   */
  RESOLVED

}
