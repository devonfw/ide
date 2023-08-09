package com.devonfw.tools.ide.env;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.devonfw.tools.ide.env.var.EnvironmentVariables;
import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Implementation of {@link Environment}.
 */
final class EnvironmentImpl implements Environment {

  private final Path ideHome;

  private final Path workspacePath;

  private final String workspaceName;

  private final EnvironmentVariables variables;

  private EnvironmentImpl(IdeLogger logger, Path ideHome, String wksName) {

    super();
    if (wksName == null) {
      wksName = WORKSPACE_MAIN;
    }
    final String userDir = System.getProperty("user.dir");
    String name1 = "";
    String name2 = "";
    if (ideHome == null) {
      Path cwd = Paths.get(userDir);
      while (cwd != null) {
        if (isIdeHome(cwd)) {
          if (FOLDER_WORKSPACES.equals(name1)) {
            wksName = name2;
          }
          ideHome = cwd;
          break;
        }
        name2 = name1;
        int nameCount = cwd.getNameCount();
        name1 = cwd.getName(nameCount - 1).toString();
        cwd = getParentPath(cwd);
      }
    }
    this.workspaceName = wksName;
    if (ideHome == null) {
      logger.info("You are not inside a devonfw-ide installation: " + userDir);
      this.ideHome = null;
      this.workspacePath = null;
    } else {
      this.ideHome = ideHome;
      this.workspacePath = this.ideHome.resolve(FOLDER_WORKSPACES).resolve(this.workspaceName);
    }
    this.variables = createVariables(logger);
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

  private EnvironmentVariables createVariables(IdeLogger logger) {

    EnvironmentVariables system = EnvironmentVariables.ofSystem(logger);
    EnvironmentVariables user = system.extend(Paths.get(System.getProperty("user.home")));
    EnvironmentVariables raw;
    if (this.ideHome == null) {
      raw = user;
    } else {
      EnvironmentVariables scripts = user.extend(this.ideHome.resolve("scripts"));
      EnvironmentVariables settings = scripts.extend(this.ideHome.resolve("settings"));
      // TODO should we keep this workspace properties? Was this feature ever used?
      EnvironmentVariables workspace = settings.extend(this.workspacePath);
      EnvironmentVariables conf = workspace.extend(this.ideHome.resolve("conf"));
      raw = conf;
    }
    return raw.resolved();
  }

  @Override
  public Path getIdeHome() {

    return this.ideHome;
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
  public EnvironmentVariables getVariables() {

    return this.variables;
  }

  static EnvironmentImpl of(IdeLogger logger, Path ideHome, String workspace) {

    return new EnvironmentImpl(logger, ideHome, workspace);
  }
}
