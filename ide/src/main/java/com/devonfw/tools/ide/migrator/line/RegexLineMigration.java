package com.devonfw.tools.ide.migrator.line;

import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Implementation of {@link LineMigration} based on regex {@link Pattern} replacement.
 */
public class RegexLineMigration extends AbstractLineMigration {

  private final Pattern regex;

  private final String replacement;

  /**
   * The constructor.
   *
   * @param regex the {@link Pattern} to match.
   * @param replacement the replacement.
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   */
  public RegexLineMigration(Pattern regex, String replacement, FileFilter fileFilter) {

    super(fileFilter);
    this.regex = regex;
    this.replacement = replacement;
  }

  @Override
  protected String migrate(String line) {

    return this.regex.matcher(line).replaceAll(this.replacement);
  }

}
