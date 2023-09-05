package com.devonfw.tools.ide.context;

import java.nio.file.Path;

import com.devonfw.tools.ide.cli.CliAbortException;
import com.devonfw.tools.ide.cli.CliException;
import com.devonfw.tools.ide.common.SystemInfo;
import com.devonfw.tools.ide.environment.EnvironmentVariables;
import com.devonfw.tools.ide.environment.EnvironmentVariablesType;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.process.ProcessContext;
import com.devonfw.tools.ide.url.model.UrlMetadata;
import com.devonfw.tools.ide.variable.IdeVariables;

/**
 * Interface for interaction with the user allowing to input and output information.
 */
public interface IdeContext extends IdeLogger {

  /** The name of the workspaces folder. */
  String FOLDER_WORKSPACES = "workspaces";

  /** The name of the settings folder. */
  String FOLDER_SETTINGS = "settings";

  /** The name of the software folder. */
  String FOLDER_SOFTWARE = "software";

  /** The name of the conf folder for project specific user configurations. */
  String FOLDER_CONF = "conf";

  /**
   * The base folder name of the IDE inside IDE_ROOT. Intentionally starting with an underscore and not a dot (to
   * prevent effects like OS hiding, maven filtering, .gitignore, etc.).
   */
  String FOLDER_IDE = "_ide";

  /** The default for {@link #getWorkspaceName()}. */
  String WORKSPACE_MAIN = "main";

  /**
   * @return {@code true} in case of quiet mode (reduced output), {@code false} otherwise.
   */
  boolean isQuietMode();

  /**
   * @return {@code true} in case of batch mode (no {@link #question(String) user-interaction}), {@code false}
   *         otherwise.
   */
  boolean isBatchMode();

  /**
   * @return {@code true} in case of force mode, {@code false} otherwise.
   */
  boolean isForceMode();

  /**
   * @return {@code true} if offline mode is activated (-o/--offline), {@code false} otherwise.
   */
  boolean isOfflineMode();

  /**
   * @return {@code true} if {@link #isOfflineMode() offline mode} is active or we are NOT {@link #isOnline() online},
   *         {@code false} otherwise.
   */
  default boolean isOffline() {

    return isOfflineMode() || !isOnline();
  }

  /**
   * @return {@code true} if we are currently online (Internet access is available), {@code false} otherwise.
   */
  boolean isOnline();

  /**
   * @param question the question to ask.
   * @return {@code true} if the user answered with "yes", {@code false} otherwise ("no").
   */
  default boolean question(String question) {

    String yes = "yes";
    String option = question(question, yes, "no");
    if (yes.equals(option)) {
      return true;
    }
    return false;
  }

  /**
   * @param <O> type of the option. E.g. {@link String}.
   * @param question the question to ask.
   * @param options the available options for the user to answer. There should be at least two options given as
   *        otherwise the question cannot make sense.
   * @return the option selected by the user as answer.
   */
  @SuppressWarnings("unchecked")
  <O> O question(String question, O... options);

  /**
   * Will ask the given question. If the user answers with "yes" the method will return and the process can continue.
   * Otherwise if the user answers with "no" an exception is thrown to abort further processing.
   *
   * @param question the yes/no question to {@link #question(String) ask}.
   * @throws CliAbortException if the user answered with "no" and further processing shall be aborted.
   */
  default void askToContinue(String question) {

    boolean yesContinue = question(question);
    if (!yesContinue) {
      throw new CliAbortException();
    }
  }

  /**
   * @param purpose the purpose why Internet connection is required.
   * @throws CliException if you are {@link #isOffline() offline}.
   */
  default void requireOnline(String purpose) {

    if (isOfflineMode()) {
      throw new CliException("You are offline but Internet access is required for " + purpose, 23);
    }
  }

  /**
   * @return the {@link SystemInfo}.
   */
  SystemInfo getSystemInfo();

  /**
   * @return the {@link EnvironmentVariables} with full inheritance.
   */
  EnvironmentVariables getVariables();

  /**
   * @return the {@link Path} to the IDE instance directory. You can have as many IDE instances on the same computer as
   *         independent tenants for different isolated projects.
   * @see com.devonfw.tools.ide.variable.IdeVariables#IDE_HOME
   */
  Path getIdeHome();

  /**
   * @return the {@link Path} to the IDE installation root directory. This is the top-level folder where the
   *         {@link #getIdeHome() IDE instances} are located as sub-folder. There is a reserved ".ide" folder where
   *         central IDE data is stored such as the {@link #getUrlsPath() download metadata} and the central software
   *         repository.
   * @see com.devonfw.tools.ide.variable.IdeVariables#IDE_ROOT
   */
  Path getIdeRoot();

  /**
   * @return the {@link Path} to the download metadata (ide-urls). Here a git repository is cloned and updated (pulled)
   *         to always have the latest metadata to download tools.
   * @see com.devonfw.tools.ide.url.model.folder.UrlRepository
   */
  Path getUrlsPath();

  /**
   * @return the {@link UrlMetadata}. Will be lazily instantiated and thereby automatically be cloned or pulled (by
   *         default).
   */
  UrlMetadata getUrls();

  /**
   * @return the {@link Path} to the download cache. All downloads will be placed here using a unique naming pattern
   *         that allows to reuse these artifacts. So if the same artifact is requested again it will be taken from the
   *         cache to avoid downloading it again.
   */
  Path getDownloadPath();

  /**
   * @return the {@link Path} to the software folder. All tools will be "installed" here as a sub-folder named after the
   *         according tool.
   */
  Path getSoftwarePath();

  /**
   * @return the {@link Path} to the central tool repository. All tools will be installed in this location using the
   *         directory naming schema of {@code «repository»/«tool»/«edition»/«version»/}. Actual {@link #getIdeHome()
   *         IDE instances} will only contain symbolic links to the physical tool installations in this repository. This
   *         allows to share and reuse tool installations across multiple {@link #getIdeHome() IDE instances}. The
   *         variable {@code «repository»} is typically {@code default} for the tools from our standard
   *         {@link #getUrlsPath() ide-urls download metadata} but this will differ for custom tools from a private
   *         repository.
   */
  Path getToolRepository();

  /**
   * @return the {@link Path} to the users home directory. Typically initialized via the
   *         {@link System#getProperty(String) system property} "user.home".
   * @see com.devonfw.tools.ide.variable.IdeVariables#HOME
   */
  Path getUserHome();

  /**
   * @return the {@link Path} to the ".ide" subfolder in the {@link #getUserHome() users home directory}.
   */
  Path getUserHomeIde();

  /**
   * @return the {@link Path} to the {@code settings} folder with the cloned git repository containing the project
   *         configuration.
   */
  Path getSettingsPath();

  /**
   * @return the {@link Path} to the {@code conf} folder with instance specific tool configurations and the
   *         {@link EnvironmentVariablesType#CONF user specific project configuration}.
   */
  Path getConfPath();

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
   * @return the value of the system {@link IdeVariables#PATH PATH} variable. It is automatically extended according to
   *         the tools available in {@link #getSoftwarePath() software path} unless {@link #getIdeHome() IDE_HOME} was
   *         not found.
   */
  String getPath();

  /**
   * @param target the {@link Path} to the target folder where the git repository should be cloned or pulled. It is not
   *        the parent directory where git will by default create a sub-folder by default on clone but the final folder
   *        that will contain the ".git" subfolder.
   * @param gitRepoUrl the git remote URL to clone from. May be suffixed with a hash-sign ('#') followed by the branch
   *        name to check-out.
   */
  void gitPullOrClone(Path target, String gitRepoUrl);

  /**
   * @return a new {@link ProcessContext} to {@link ProcessContext#run(String...) run} external commands.
   */
  ProcessContext newProcess();

  /**
   * Creates the entire {@link Path} as directories if not already existing.
   *
   * @param directory the {@link Path} to
   *        {@link java.nio.file.Files#createDirectories(Path, java.nio.file.attribute.FileAttribute...) create}.
   */
  void mkdirs(Path directory);

}
