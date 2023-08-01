package com.devonfw.tools.ide.migrator.line;

import java.io.File;
import java.io.FileFilter;

/**
 * Abstract base implementation of {@link LineMigration}.
 */
public abstract class AbstractLineMigration implements LineMigration {

  private final FileFilter fileFilter;

  private boolean disabled;

  /**
   * The constructor.
   *
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   */
  public AbstractLineMigration(FileFilter fileFilter) {

    super();
    this.fileFilter = fileFilter;
  }

  @Override
  public void init(File file) {

    this.disabled = !this.fileFilter.accept(file);
  }

  @Override
  public final String migrateLine(String line) {

    if (this.disabled) {
      return line;
    }
    return migrate(line);
  }

  /**
   * @see #migrateLine(String)
   * @param line the line to migrate.
   * @return the migrated line.
   */
  protected abstract String migrate(String line);

  @Override
  public void clear() {

    this.disabled = false;
  }

}
