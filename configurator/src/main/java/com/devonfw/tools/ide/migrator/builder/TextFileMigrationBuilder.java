package com.devonfw.tools.ide.migrator.builder;

import java.io.FileFilter;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.file.FileFilterAll;
import com.devonfw.tools.ide.migrator.file.TextFileMigration;
import com.devonfw.tools.ide.migrator.line.LineMigration;
import com.devonfw.tools.ide.migrator.line.RegexLineMigration;
import com.devonfw.tools.ide.migrator.line.StringReplaceLineMigration;

/**
 * Builder for textual file migrations.
 */
public class TextFileMigrationBuilder {

  private final MigrationStepBuilder parent;

  private final TextFileMigration migration;

  /**
   * The constructor.
   *
   * @param parent the parent builder.
   * @param pattern the {@link Pattern} for the filename to match and migrate.
   */
  public TextFileMigrationBuilder(MigrationStepBuilder parent, Pattern pattern) {

    super();
    this.parent = parent;
    this.migration = new TextFileMigration(pattern);
  }

  /**
   * @param pattern the {@link Pattern} to match.
   * @param replacement the replacement for the given {@link Pattern}. May contain variable expressions (e.g. "$1") to
   *        reference regex groups.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replaceRegex(Pattern pattern, String replacement) {

    return replaceRegex(pattern, replacement, FileFilterAll.INSTANCE);
  }

  /**
   * @param pattern the {@link Pattern} to match.
   * @param replacement the replacement for the given {@link Pattern}. May contain variable expressions (e.g. "$1") to
   *        reference regex groups.
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replaceRegex(Pattern pattern, String replacement, FileFilter fileFilter) {

    this.migration.getLineMigrations().add(new RegexLineMigration(pattern, replacement, fileFilter));
    return this;
  }

  /**
   * @param pattern the {@link Pattern} to match as {@link String}.
   * @param replacement the replacement for the given {@link Pattern}. May contain variable expressions (e.g. "$1") to
   *        reference regex groups.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replaceRegex(String pattern, String replacement) {

    return replaceRegex(Pattern.compile(pattern), replacement);
  }

  /**
   * @param pattern the {@link Pattern} to match as {@link String}.
   * @param replacement the replacement for the given {@link Pattern}. May contain variable expressions (e.g. "$1") to
   *        reference regex groups.
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replaceRegex(String pattern, String replacement, FileFilter fileFilter) {

    return replaceRegex(Pattern.compile(pattern), replacement, fileFilter);
  }

  /**
   * @param search the plain {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replace(String search, String replacement) {

    return replace(search, replacement, FileFilterAll.INSTANCE);
  }

  /**
   * @param search the plain {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   * @param fileFilter the {@link FileFilter} to accept the files that shall be migrated.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder replace(String search, String replacement, FileFilter fileFilter) {

    this.migration.getLineMigrations().add(new StringReplaceLineMigration(search, replacement, fileFilter));
    return this;
  }

  /**
   * @param lineMigration the custom {@link LineMigration} to add.
   * @return {@code this}.
   */
  public TextFileMigrationBuilder add(LineMigration lineMigration) {

    this.migration.getLineMigrations().add(lineMigration);
    return this;
  }

  /**
   * @return the parent builder after this migration is complete.
   */
  public MigrationStepBuilder and() {

    this.parent.step.getFileMigrations().add(this.migration);
    return this.parent;
  }
}
