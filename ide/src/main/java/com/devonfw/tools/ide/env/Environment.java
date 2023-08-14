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
   * @return the {@link Path} to the IDE instance directory. You can have as many IDE instances on the same computer as
   *         independent tenants for different isolated projects.
   * @see com.devonfw.tools.ide.env.var.def.IdeVariables#IDE_HOME
   */
  Path getIdeHome();

  /**
   * @return the {@link Path} to the IDE installation root directory. This is the top-level folder where the
   *         {@link #getIdeHome() IDE instances} are located as sub-folder. There is a reserved ".ide" folder where
   *         central IDE data is stored such as the {@link #getDownloadMetadata() download metadata} and the central
   *         software repository.
   * @see com.devonfw.tools.ide.env.var.def.IdeVariables#IDE_ROOT
   */
  Path getIdeRoot();

  /**
   * @return the {@link Path} to the download metadata (ide-urls). Here a git repository is cloned and updated (pulled)
   *         to always have the latest metadata to download tools.
   * @see com.devonfw.tools.ide.url.model.folder.UrlRepository
   */
  Path getDownloadMetadata();

  /**
   * @return the {@link Path} to the download cache. All downloads will be placed here using a unique naming pattern
   *         that allows to reuse these artifacts. So if the same artifact is requested again it will be taken from the
   *         cache to avoid downloading it again.
   */
  Path getDownloadCache();

  /**
   * @return the {@link Path} to the central tool repository. All tools will be installed in this location using the
   *         directory naming schema of {@code «repository»/«tool»/«edition»/«version»/}. Actual {@link #getIdeHome()
   *         IDE instances} will only contain symbolic links to the physical tool installations in this repository. This
   *         allows to share and reuse tool installations across multiple {@link #getIdeHome() IDE instances}. The
   *         variable {@code «repository»} is typically {@code default} for the tools from our standard
   *         {@link #getDownloadMetadata() download metadata} but this will differ for custom tools from a private
   *         repository.
   */
  Path getToolRepository();

  /**
   * @return the {@link Path} to the users home directory. Typically initialized via the
   *         {@link System#getProperty(String) system property} "user.home".
   * @see com.devonfw.tools.ide.env.var.def.IdeVariables#HOME
   */
  Path getUserHome();

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

    return of(logger, null, null, null, null);
  }

  /**
   * @param logger the {@link IdeLogger}.
   * @param userDir the optional {@link Path} to current working directory. Typically {@code null} to default to
   *        "user.dir". If both {@code userDir} and {@code workspace} are non-null, then this {@link Path} is used as
   *        {@link #getIdeHome() IDE_HOME} without auto-detection by directory-traversal.
   * @param workspace the pre-defined {@link #getWorkspaceName() workspace name}. Typically {@code null} for
   *        auto-detection.
   * @param ideRoot the optional {@link Path} to {@link #getIdeRoot() IDE_ROOT}. Typically {@code null} to default to
   *        the {@link Path#getParent() parent} of {@link #getIdeHome() IDE_HOME}.
   * @param userHome the path relative to {@link #getIdeHome() IDE_HOME} for {@link #getUserHome() HOME} for testing.
   *        Typically {@code null} to use the default.
   * @return the new {@link Environment}.
   */
  static Environment of(IdeLogger logger, Path userDir, String workspace, Path ideRoot, String userHome) {

    return EnvironmentImpl.of(logger, userDir, workspace, ideRoot, userHome);
  }

}
