package com.devonfw.tools.ide.url;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.tools.ide.url.updater.UpdateManager;

/**
 * This is the main program to run the updater of {@code ide-urls} repository. It is run nightly via an automated
 * process.
 */
public class UpdateInitiator {
  private static final Logger logger = LoggerFactory.getLogger(UpdateInitiator.class.getName());

  /**
   * @param args the command-line arguments. arg[0] points to the {@code ide-urls} repository. arg[1] defines a timeout
   * for GitHub actions in seconds.
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      logger.error("Error: Missing path to repository as well as missing timeout as command line arguments.");
      logger.error("Usage: java UpdateInitiator <path_to_repository>");
      System.exit(1);
    }
    String pathToRepo = args[0];
    long timeout = 0;
    try {
      timeout = Long.parseLong(args[1]);
    } catch (NumberFormatException e) {
      logger.error("Error: Provided timeout format is not valid.", e);
      System.exit(1);
    }
    Path repoPath = Path.of(pathToRepo);
    if (!repoPath.toFile().isDirectory()) {
      logger.error("Error: Provided path is not a valid directory.");
      System.exit(1);
    }

    UpdateManager updateManager = new UpdateManager(repoPath, timeout);
    updateManager.updateAll();
  }
}
