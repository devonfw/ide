package com.devonfw.tools.ide.migrator;

import java.io.File;

/**
 * Interface for a migration.
 */
public interface Migration {

  /**
   * @param projectFolder the {@link File} (directory) to migrate.
   * @throws Exception on error.
   */
  void migrate(File projectFolder) throws Exception;

}
