package com.devonfw.tools.ide.migrator.file;

import java.io.File;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.Migration;

/**
 * {@link Migration} for a single {@link File}.
 */
public abstract class FileMigration implements Migration {

  private final Pattern namePattern;

  /**
   * The constructor.
   *
   * @param namePattern the {@link Pattern} used to match the {@link File#getName() filename} to apply this migration
   *        to.
   */
  public FileMigration(Pattern namePattern) {

    super();
    this.namePattern = namePattern;
  }

  @Override
  public final void migrate(File file) throws Exception {

    String name = file.getName();
    if (this.namePattern.matcher(name).matches()) {
      migrateFile(file);
    }
  }

  /**
   * @param file the matching {@link File} to migrate.
   * @throws Exception on error.
   */
  protected abstract void migrateFile(File file) throws Exception;

}
