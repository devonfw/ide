package com.devonfw.tools.ide.url;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.tools.ide.url.updater.UpdateManager;

public class UpdateInitiator {
  private static final Logger logger = LoggerFactory.getLogger(UpdateInitiator.class.getName());

  public static void main(String[] args) {

    if (args.length == 0) {
      logger.error("Error: Missing path to repository as command line argument.");
      logger.error("Usage: java UpdateInitiator <path_to_repository>");
      System.exit(1);
    }
    String pathToRepo = args[0];
    Path repoPath = Path.of(pathToRepo);
    if (!repoPath.toFile().isDirectory()) {
      logger.error("Error: Provided path is not a valid directory.");
      System.exit(1);
    }
    UpdateManager updateManager = new UpdateManager(repoPath);
    updateManager.updateAll();
  }
}
