package com.devonfw.tools.ide.migrator.line;

import java.io.File;

/**
 * Interface to migrate a single line of text (code, etc.)
 */
public interface LineMigration {

  /**
   * @param file the {@link File} to start migrating.
   */
  void init(File file);

  /**
   * @param line the line of text to process.
   * @return the migrated line (may be the original or a modified one).
   */
  String migrateLine(String line);

  /**
   * Called at the beginning of each new file to clear potential state.
   */
  void clear();

}
