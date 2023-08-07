package com.devonfw.tools.ide.migrator.builder;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.file.XmlFileMigration;
import com.devonfw.tools.ide.migrator.xml.XmlCommentNodeReplacement;
import com.devonfw.tools.ide.migrator.xml.XmlNamespaceReplacement;
import com.devonfw.tools.ide.migrator.xml.XmlStringReplacement;

/**
 * Builder for an {@link XmlFileMigration}.
 */
public class XmlMigrationBuilder {

  private final MigrationStepBuilder parent;

  /** The actual migration to build. */
  protected final XmlFileMigration migration;

  /**
   * The constructor.
   *
   * @param parent the parent builder.
   * @param pattern the {@link Pattern} for the filenames to match.
   */
  public XmlMigrationBuilder(MigrationStepBuilder parent, Pattern pattern) {

    super();
    this.parent = parent;
    this.migration = new XmlFileMigration(pattern);
  }

  /**
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   * @return {@code this}.
   */
  public XmlMigrationBuilder replaceText(String search, String replacement) {

    this.migration.getMigrations().add(new XmlStringReplacement(search, replacement));
    return this;
  }

  /**
   * @return the parent builder after this XML migration is complete.
   */
  public MigrationStepBuilder and() {

    this.parent.step.getFileMigrations().add(this.migration);
    return this.parent;
  }

  /**
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   * @return {@code this}.
   */
  public XmlMigrationBuilder replaceNamespace(String search, String replacement) {

    this.migration.getMigrations().add(new XmlNamespaceReplacement(search, replacement));
    return this;
  }

  /**
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   * @return {@code this}.
   */
  public XmlMigrationBuilder replaceCommentNode(String search, String replacement) {

    this.migration.getMigrations().add(new XmlCommentNodeReplacement(search, replacement));
    return this;
  }
}
