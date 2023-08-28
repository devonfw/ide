package com.devonfw.tools.ide.env;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.devonfw.tools.ide.env.var.AbstractEnvironmentVariables;
import com.devonfw.tools.ide.env.var.EnvironmentVariables;
import com.devonfw.tools.ide.env.var.EnvironmentVariablesType;
import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Implementation of {@link Environment}.
 */
final class EnvironmentImpl implements Environment {

  /**
   * The base folder name of the IDE inside IDE_ROOT. Intentionally starting with an underscore and not a dot (to
   * prevent effects like OS hiding, maven filtering, .gitignore, etc.).
   */
  private static final String IDE_ROOT_BASE_FOLDER = "_ide";

  private final IdeLogger logger;

  private final Path ideHome;

  private final Path ideRoot;

  private final Path confPath;

  private final Path settingsPath;

  private final Path softwarePath;

  private final Path workspacePath;

  private final String workspaceName;

  private final Path downloadMetadata;

  private final Path downloadCache;

  private final Path toolRepository;

  private final Path userHome;

  private final Path userHomeIde;

  private final EnvironmentVariables variables;

  private EnvironmentImpl(IdeLogger logger, Path userDir, String workspace, Path ideRoot, String userHome) {

    super();
    this.logger = logger;
    // determine if we need to auto-detect IDE home
    boolean autoDetectIdeHome = (userDir == null) || (workspace == null);
    if (workspace == null) {
      workspace = WORKSPACE_MAIN;
    }
    if (userDir == null) {
      userDir = Paths.get(System.getProperty("user.dir"));
    }
    if (autoDetectIdeHome) {
      Path cwd = userDir;
      String name1 = "";
      String name2 = "";
      while (cwd != null) {
        logger.trace("Looking for IDE_HOME in {}", cwd);
        if (isIdeHome(cwd)) {
          if (FOLDER_WORKSPACES.equals(name1)) {
            workspace = name2;
          }
          break;
        }
        name2 = name1;
        int nameCount = cwd.getNameCount();
        name1 = cwd.getName(nameCount - 1).toString();
        cwd = getParentPath(cwd);
      }
      this.ideHome = cwd;
    } else {
      this.ideHome = userDir;
    }
    this.workspaceName = workspace;
    if (this.ideHome == null) {
      logger.info("You are not inside a devonfw-ide installation: " + userDir);
      this.workspacePath = null;
      this.ideRoot = ideRoot;
      this.confPath = null;
      this.settingsPath = null;
      this.softwarePath = null;
    } else {
      this.workspacePath = this.ideHome.resolve(FOLDER_WORKSPACES).resolve(this.workspaceName);
      if (ideRoot == null) {
        this.ideRoot = this.ideHome.getParent();
      } else {
        this.ideRoot = ideRoot;
      }
      this.confPath = this.ideHome.resolve(FOLDER_CONF);
      this.settingsPath = this.ideHome.resolve(FOLDER_SETTINGS);
      this.softwarePath = this.ideHome.resolve(FOLDER_SOFTWARE);
    }
    if (this.ideRoot == null) {
      this.toolRepository = null;
      this.downloadMetadata = null;
    } else {
      Path ideBase = this.ideRoot.resolve(IDE_ROOT_BASE_FOLDER);
      this.toolRepository = ideBase.resolve("software");
      this.downloadMetadata = ideBase.resolve("urls");
    }
    if (userHome == null) {
      this.userHome = Paths.get(System.getProperty("user.home"));
    } else {
      // only for testing...
      this.userHome = this.ideHome.resolve(userHome);
    }
    this.userHomeIde = this.userHome.resolve(".ide");
    this.downloadCache = this.userHome.resolve("Downloads/ide");
    this.variables = createVariables();
  }

  private boolean isIdeHome(Path path) {

    if (!Files.isRegularFile(path.resolve("setup"))) {
      return false;
    } else if (!Files.isDirectory(path.resolve("scripts"))) {
      return false;
    } else if (path.toString().endsWith("/scripts/src/main/resources")) {
      // TODO does this still make sense for our new Java based product?
      return false;
    }
    return true;
  }

  private Path getParentPath(Path path) {

    try {
      Path linkDir = path.toRealPath();
      if (!path.equals(linkDir)) {
        return linkDir;
      } else {
        return path.getParent();
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  private EnvironmentVariables createVariables() {

    AbstractEnvironmentVariables system = EnvironmentVariables.ofSystem(this.logger);
    AbstractEnvironmentVariables user = extendVariables(system, this.userHomeIde, EnvironmentVariablesType.USER);
    AbstractEnvironmentVariables settings = extendVariables(user, this.settingsPath, EnvironmentVariablesType.SETTINGS);
    // TODO should we keep this workspace properties? Was this feature ever used?
    AbstractEnvironmentVariables workspace = extendVariables(settings, this.workspacePath,
        EnvironmentVariablesType.WORKSPACE);
    AbstractEnvironmentVariables conf = extendVariables(workspace, this.confPath, EnvironmentVariablesType.CONF);
    return conf.resolved();
  }

  private AbstractEnvironmentVariables extendVariables(AbstractEnvironmentVariables envVariables, Path propertiesPath,
      EnvironmentVariablesType type) {

    Path propertiesFile = null;
    if (propertiesPath == null) {
      this.logger.trace("Configuration directory for type {} does not exist.", type);
    } else if (Files.isDirectory(propertiesPath)) {
      propertiesFile = propertiesPath.resolve(EnvironmentVariables.DEFAULT_PROPERTIES);
      boolean legacySupport = (type != EnvironmentVariablesType.USER);
      if (legacySupport && !Files.exists(propertiesFile)) {
        Path legacyFile = propertiesPath.resolve(EnvironmentVariables.LEGACY_PROPERTIES);
        if (Files.exists(legacyFile)) {
          propertiesFile = legacyFile;
        }
      }
    } else {
      this.logger.debug("Configuration directory {} does not exist.", propertiesPath);
    }
    return envVariables.extend(propertiesFile, type);

  }

  @Override
  public Path getIdeHome() {

    return this.ideHome;
  }

  @Override
  public Path getIdeRoot() {

    return this.ideRoot;
  }

  @Override
  public Path getUserHome() {

    return this.userHome;
  }

  @Override
  public Path getUserHomeIde() {

    return this.userHomeIde;
  }

  @Override
  public Path getSettingsPath() {

    return this.settingsPath;
  }

  @Override
  public Path getConfPath() {

    return this.confPath;
  }

  @Override
  public Path getSoftwarePath() {

    return this.softwarePath;
  }

  @Override
  public String getWorkspaceName() {

    return this.workspaceName;
  }

  @Override
  public Path getWorkspacePath() {

    return this.workspacePath;
  }

  @Override
  public Path getDownloadCache() {

    return this.downloadCache;
  }

  @Override
  public Path getDownloadMetadata() {

    return this.downloadMetadata;
  }

  @Override
  public Path getToolRepository() {

    return this.toolRepository;
  }

  @Override
  public EnvironmentVariables getVariables() {

    return this.variables;
  }

  static EnvironmentImpl of(IdeLogger logger, Path userDir, String workspace, Path ideRoot, String userHome) {

    return new EnvironmentImpl(logger, userDir, workspace, ideRoot, userHome);
  }
}
