package com.devonfw.tools.ide.migrator.line;

import java.io.FileFilter;

/**
 * Implementation of {@link LineMigration} for simple string replacement.
 */
public class StringReplaceLineMigration extends AbstractLineMigration {

  private final String search;

  private final String replacement;

  /**
   * The constructor.
   *
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the {@code search} {@link String}.
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   */
  public StringReplaceLineMigration(String search, String replacement, FileFilter fileFilter) {

    super(fileFilter);
    this.search = search;
    this.replacement = replacement;
  }

  @Override
  protected String migrate(String line) {

    return line.replace(this.search, this.replacement);
  }

}
