package com.devonfw.tools.ide.url;

import com.devonfw.tools.ide.url.updater.ChecksumGenerator;
import com.devonfw.tools.ide.url.updater.UpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class UpdateInitiator {
	private static final Logger logger = LoggerFactory.getLogger(UpdateInitiator.class.getName());

	public static void main(String[] args) {
		if (args.length == 0) {
			logger.error("Error: Missing path to repository as command line argument.");
			logger.error("Usage: java UpdateInitiator <path_to_repository>");
			System.exit(1);
		}
		String pathToRepo = "C:\\Users\\alfeil\\Desktop\\ide-urls";
		Path repoPath = Path.of(pathToRepo);
		if (!repoPath.toFile().isDirectory()) {
			logger.error("Error: Provided path is not a valid directory.");
			System.exit(1);
		}
		UpdateManager updateManager = new UpdateManager(repoPath);
		updateManager.updateAll();
		ChecksumGenerator checksumGenerator = new ChecksumGenerator();
		checksumGenerator.generateChecksums(repoPath.toFile().listFiles());
	}
}
