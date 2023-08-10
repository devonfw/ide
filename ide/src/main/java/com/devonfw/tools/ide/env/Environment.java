package com.devonfw.tools.ide.env;

import java.nio.file.Path;

import com.devonfw.tools.ide.env.var.EnvironmentVariables;
import com.devonfw.tools.ide.log.IdeLogger;

/**
 * The central environment of the IDE.
 */
public interface Environment {

  /** The name of the workspaces folder. */
  String FOLDER_WORKSPACES = "workspaces";

  /** The default for {@link #getWorkspaceName()}. */
  String WORKSPACE_MAIN = "main";

  /**
   * @return the {@link EnvironmentVariables} with full inheritance.
   */
  EnvironmentVariables getVariables();

  /**
   * @return the {@link Path} to the IDE installation root directory.
   * @see com.devonfw.tools.ide.env.var.def.IdeVariables#IDE_HOME
   */
  Path getIdeHome();

  /**
   * @return the {@link Path} to the workspace.
   * @see #getWorkspaceName()
   */
  Path getWorkspacePath();

  /**
   * @return the name of the workspace. Defaults to {@link #WORKSPACE_MAIN}.
   */
  String getWorkspaceName();

  /**
   * @param logger the {@link IdeLogger}.
   * @return the new {@link Environment}.
   */
  static Environment of(IdeLogger logger) {

    return of(logger, null, null);
  }

  /**
   * @param logger the {@link IdeLogger}.
   * @param ideHome the optional {@link Path} to a pre-defined {@link #getIdeHome() IDE_HOME}. Typically {@code null}
   *        for auto-detection.
   * @param workspace the pre-defined {@link #getWorkspaceName() workspace name}. Typically {@code null} for
   *        auto-detection.
   * @return the new {@link Environment}.
   */
  static Environment of(IdeLogger logger, Path ideHome, String workspace) {

    return EnvironmentImpl.of(logger, ideHome, workspace);
  }

}
