package com.devonfw.tools.ide.context;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import com.devonfw.tools.ide.environment.AbstractEnvironmentVariables;
import com.devonfw.tools.ide.environment.EnvironmentVariables;
import com.devonfw.tools.ide.environment.EnvironmentVariablesType;
import com.devonfw.tools.ide.environment.VariableLine;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeSubLogger;
import com.devonfw.tools.ide.log.IdeSubLoggerNone;
import com.devonfw.tools.ide.process.ProcessContext;
import com.devonfw.tools.ide.process.ProcessContextImpl;
import com.devonfw.tools.ide.process.ProcessErrorHandling;
import com.devonfw.tools.ide.url.model.UrlMetadata;
import com.devonfw.tools.ide.variable.IdeVariables;

/**
 * Abstract base implementation of {@link IdeContext}.
 */
public abstract class AbstractIdeContext implements IdeContext {

  private final Map<IdeLogLevel, IdeSubLogger> loggers;

  private final Path ideHome;

  private final Path ideRoot;

  private final Path confPath;

  private final Path settingsPath;

  private final Path softwarePath;

  private final Path workspacePath;

  private final String workspaceName;

  private final Path urlsPath;

  private final Path downloadPath;

  private final Path toolRepository;

  private final Path userHome;

  private final Path userHomeIde;

  private final String path;

  private final EnvironmentVariables variables;

  private boolean offlineMode;

  private boolean forceMode;

  private boolean batchMode;

  private boolean quietMode;

  private UrlMetadata urlMetadata;

  /**
   * The constructor.
   *
   * @param minLogLevel the minimum {@link IdeLogLevel} to enable. Should be {@link IdeLogLevel#INFO} by default.
   * @param factory the {@link Function} to create {@link IdeSubLogger} per {@link IdeLogLevel}.
   * @param userDir the optional {@link Path} to current working directory.
   */
  public AbstractIdeContext(IdeLogLevel minLogLevel, Function<IdeLogLevel, IdeSubLogger> factory, Path userDir) {

    super();
    this.loggers = new HashMap<>();
    for (IdeLogLevel level : IdeLogLevel.values()) {
      IdeSubLogger logger;
      if (level.ordinal() < minLogLevel.ordinal()) {
        logger = new IdeSubLoggerNone(level);
      } else {
        logger = factory.apply(level);
      }
      this.loggers.put(level, logger);
    }
    String workspace = WORKSPACE_MAIN;
    if (userDir == null) {
      userDir = Paths.get(System.getProperty("user.dir"));
    }
    // detect IDE_HOME and WORKSPACE
    Path cwd = userDir;
    String name1 = "";
    String name2 = "";
    while (cwd != null) {
      trace("Looking for IDE_HOME in {}", cwd);
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
    // detection completed, initializing variables
    this.ideHome = cwd;
    this.workspaceName = workspace;
    if (this.ideHome == null) {
      info("You are not inside a devonfw-ide installation: " + userDir);
      this.workspacePath = null;
      this.ideRoot = null;
      this.confPath = null;
      this.settingsPath = null;
      this.softwarePath = null;
    } else {
      info("IDE environment variables have been set for {} in workspace {}", this.ideHome, this.workspaceName);
      this.workspacePath = this.ideHome.resolve(FOLDER_WORKSPACES).resolve(this.workspaceName);
      Path ideRootPath = this.ideHome.getParent();
      String root = null;
      if (!isTest()) {
        root = System.getenv("IDE_ROOT");
      }
      if (root != null) {
        Path rootPath = Paths.get(root);
        if (Files.isDirectory(rootPath)) {
          if (!ideRootPath.equals(rootPath)) {
            warning("Variable IDE_ROOT is set to '{}' but for your project '{}' would have been expected.");
            ideRootPath = rootPath;
          }
          ideRootPath = this.ideHome.getParent();
        } else {
          warning("Variable IDE_ROOT is not set to a valid directory '{}'." + root);
          ideRootPath = null;
        }
      }
      this.ideRoot = ideRootPath;
      this.confPath = this.ideHome.resolve(FOLDER_CONF);
      this.settingsPath = this.ideHome.resolve(FOLDER_SETTINGS);
      this.softwarePath = this.ideHome.resolve(FOLDER_SOFTWARE);
    }
    if (this.ideRoot == null) {
      this.toolRepository = null;
      this.urlsPath = null;
    } else {
      Path ideBase = this.ideRoot.resolve(FOLDER_IDE);
      this.toolRepository = ideBase.resolve("software");
      this.urlsPath = ideBase.resolve("urls");
    }
    if (isTest()) {
      // only for testing...
      this.userHome = this.ideHome.resolve("home");
    } else {
      this.userHome = Paths.get(System.getProperty("user.home"));
    }
    this.userHomeIde = this.userHome.resolve(".ide");
    this.downloadPath = this.userHome.resolve("Downloads/ide");
    this.variables = createVariables();
    this.path = computeSystemPath();
  }

  /**
   * @return {@code true} if this is a test context for JUnits, {@code false} otherwise.
   */
  protected boolean isTest() {

    return false;
  }

  private String computeSystemPath() {

    String systemPath = System.getenv(IdeVariables.PATH.getName());
    StringBuilder sb = new StringBuilder(systemPath.length() + 128);

    try (Stream<Path> children = Files.list(this.softwarePath)) {
      Iterator<Path> iterator = children.iterator();
      while (iterator.hasNext()) {
        Path child = iterator.next();
        if (Files.isDirectory(child)) {
          Path toolHome = child;
          Path bin = child.resolve("bin");
          if (Files.isDirectory(bin)) {
            toolHome = bin;
          }
          sb.append(toolHome);
          sb.append(File.pathSeparatorChar);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to list children of " + this.softwarePath, e);
    }
    sb.append(systemPath);
    return sb.toString();
  }

  private boolean isIdeHome(Path cwd) {

    if (!Files.isRegularFile(cwd.resolve("setup"))) {
      return false;
    } else if (!Files.isDirectory(cwd.resolve("scripts"))) {
      return false;
    } else if (cwd.toString().endsWith("/scripts/src/main/resources")) {
      // TODO does this still make sense for our new Java based product?
      return false;
    }
    return true;
  }

  private Path getParentPath(Path cwd) {

    try {
      Path linkDir = cwd.toRealPath();
      if (!cwd.equals(linkDir)) {
        return linkDir;
      } else {
        return cwd.getParent();
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  private EnvironmentVariables createVariables() {

    AbstractEnvironmentVariables system = EnvironmentVariables.ofSystem(this);
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
      trace("Configuration directory for type {} does not exist.", type);
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
      debug("Configuration directory {} does not exist.", propertiesPath);
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
  public Path getDownloadPath() {

    return this.downloadPath;
  }

  @Override
  public Path getUrlsPath() {

    return this.urlsPath;
  }

  @Override
  public Path getToolRepository() {

    return this.toolRepository;
  }

  @Override
  public String getPath() {

    return this.path;
  }

  @Override
  public EnvironmentVariables getVariables() {

    return this.variables;
  }

  @Override
  public UrlMetadata getUrls() {

    if (this.urlMetadata == null) {
      if (!isTest()) {
        gitPullOrClone(this.urlsPath, "https://github.com/devonfw/ide-urls.git");
      }
      this.urlMetadata = new UrlMetadata(this.urlsPath);
    }
    return this.urlMetadata;
  }

  @Override
  public void mkdirs(Path directory) {

    try {
      Files.createDirectories(directory);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create directory " + directory, e);
    }
  }

  @Override
  public boolean isQuietMode() {

    return this.quietMode;
  }

  /**
   * @param quietMode new value of {@link #isQuietMode()}.
   */
  public void setQuietMode(boolean quietMode) {

    this.quietMode = quietMode;
  }

  @Override
  public boolean isBatchMode() {

    return this.batchMode;
  }

  /**
   * @param batchMode new value of {@link #isBatchMode()}.
   */
  public void setBatchMode(boolean batchMode) {

    this.batchMode = batchMode;
  }

  @Override
  public boolean isForceMode() {

    return this.forceMode;
  }

  /**
   * @param forceMode new value of {@link #isForceMode()}.
   */
  public void setForceMode(boolean forceMode) {

    this.forceMode = forceMode;
  }

  @Override
  public boolean isOfflineMode() {

    return this.offlineMode;
  }

  /**
   * @param offlineMode new value of {@link #isOfflineMode()}.
   */
  public void setOfflineMode(boolean offlineMode) {

    this.offlineMode = offlineMode;
  }

  @Override
  public boolean isOnline() {

    boolean online = false;
    try {
      int timeout = 1000;
      online = InetAddress.getByName("github.com").isReachable(timeout);
    } catch (Exception e) {

    }
    return online;
  }

  @Override
  public ProcessContext newProcess() {

    ProcessBuilder processBuilder = new ProcessBuilder();
    ProcessContext processContext = new ProcessContextImpl(processBuilder, this);
    Map<String, String> environment = processBuilder.environment();
    for (VariableLine var : this.variables.collectVariables()) {
      if (var.isExport()) {
        environment.put(var.getName(), var.getValue());
      }
    }
    // TODO needs to be configurable for GUI
    processBuilder.redirectInput(Redirect.INHERIT).redirectError(Redirect.INHERIT);
    return processContext;
  }

  @Override
  public void gitPullOrClone(Path target, String gitRepoUrl) {

    Objects.requireNonNull(target);
    Objects.requireNonNull(gitRepoUrl);
    if (!gitRepoUrl.startsWith("http")) {
      throw new IllegalArgumentException("Invalid git URL '" + gitRepoUrl + "'!");
    }
    ProcessContext pc = newProcess().directory(target);
    if (Files.isDirectory(target.resolve(".git"))) {
      List<String> remotes = pc.runAndGetStdOut("git", "remote");
      if (remotes.isEmpty()) {
        String message = "This is a local git repo with no remote - if you did this for testing, you may continue...\n"
            + "Do you want to ignore the problem and continue anyhow?";
        askToContinue(message);
      } else {
        pc.errorHandling(ProcessErrorHandling.WARNING);
        int exitCode = pc.run("git", "pull");
        if (exitCode != ProcessContext.SUCCESS) {
          String message = "Failed to update git repository at " + target;
          if (this.offlineMode) {
            warning(message);
            interaction("Continuing as we are in offline mode - results may be outdated!");
          } else {
            error(message);
            if (isOnline()) {
              error("See above error for details. If you have local changes, please stash or revert and retry.");
            } else {
              error(
                  "It seems you are offline - please ensure Internet connectivity and retry or activate offline mode (-o or --offline).");
            }
            askToContinue("Typically you should abort and fix the problem. Do you want to continue anyways?");
          }
        }
      }
    } else {
      String branch = null;
      int hashIndex = gitRepoUrl.indexOf("#");
      if (hashIndex != -1) {
        branch = gitRepoUrl.substring(hashIndex + 1);
        gitRepoUrl = gitRepoUrl.substring(0, hashIndex);
      }
      mkdirs(target);
      requireOnline("git clone of " + gitRepoUrl);
      if (isQuietMode()) {
        pc.run("git", "clone", "-q", "--recursive", gitRepoUrl, "--config", "core.autocrlf=false", ".");
      } else {
        pc.run("git", "clone", "--recursive", gitRepoUrl, "--config", "core.autocrlf=false", ".");
      }
      if (branch != null) {
        pc.run("git", "checkout", branch);
      }
    }
  }

  @Override
  public IdeSubLogger level(IdeLogLevel level) {

    IdeSubLogger logger = this.loggers.get(level);
    Objects.requireNonNull(logger);
    return logger;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <O> O question(String question, O... options) {

    assert (options.length >= 2);
    interaction(question);
    Map<String, O> mapping = new HashMap<>(options.length);
    int i = 1;
    for (O option : options) {
      String key = "" + option;
      addMapping(mapping, key, option);
      String numericKey = Integer.toString(i);
      if (numericKey.equals(key)) {
        trace("Options should not be numeric: " + key);
      } else {
        addMapping(mapping, numericKey, option);
      }
      interaction("Option " + numericKey + ": " + key);
    }
    O option = null;
    if (isBatchMode()) {
      if (isForceMode()) {
        option = options[0];
        interaction("" + option);
      }
    } else {
      while (option == null) {
        String answer = readLine();
        option = mapping.get(answer);
        if (option == null) {
          warning("Invalid answer: '" + answer + "' - please try again.");
        }
      }
    }
    return option;
  }

  /**
   * @return the input from the end-user (e.g. read from the console).
   */
  protected abstract String readLine();

  private static <O> void addMapping(Map<String, O> mapping, String key, O option) {

    O duplicate = mapping.put(key, option);
    if (duplicate != null) {
      throw new IllegalArgumentException("Duplicated option " + key);
    }
  }

}
