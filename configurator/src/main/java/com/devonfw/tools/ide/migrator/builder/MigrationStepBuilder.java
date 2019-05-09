package com.devonfw.tools.ide.migrator.builder;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.MigrationStep;
import com.devonfw.tools.ide.migrator.MigrationStepImpl;
import com.devonfw.tools.ide.migrator.file.TextFileMigration;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Builder to build a {@link MigrationStep}. To continue with the parent builder when complete call {@link #next()}.
 */
public class MigrationStepBuilder {

  final MigrationBuilder parent;

  final MigrationStepImpl step;

  /**
   * The constructor.
   *
   * @param parent the parent builder.
   * @param from the {@link VersionIdentifier} to migrate from.
   * @param to the {@link VersionIdentifier} to migrate to.
   */
  public MigrationStepBuilder(MigrationBuilder parent, VersionIdentifier from, VersionIdentifier to) {

    super();
    this.parent = parent;
    this.step = new MigrationStepImpl(from, to);
  }

  /**
   * @return a new builder to create a Java migration. Call {@link TextFileMigrationBuilder#and()} to continue with this
   *         builder once complete.
   */
  public TextFileMigrationBuilder java() {

    return new TextFileMigrationBuilder(this, TextFileMigration.JAVA_PATTERN);
  }

  /**
   * @return a new builder to create a migration for spring application properties. Call
   *         {@link TextFileMigrationBuilder#and()} to continue with this builder once complete.
   */
  public TextFileMigrationBuilder applicationProperties() {

    return new TextFileMigrationBuilder(this, TextFileMigration.APPLICATION_PROPERTIES_PATTERN);
  }

  /**
   * @return a new builder to create a Maven POM migration. Call {@link PomXmlMigrationBuilder#and()} to continue with
   *         this builder once complete.
   */
  public PomXmlMigrationBuilder pom() {

    return new PomXmlMigrationBuilder(this);
  }

  /**
   * @param filename the filename to match literally.
   * @return a new builder to create an XML migration. Call {@link XmlMigrationBuilder#and()} to continue with this
   *         builder once complete.
   */
  public XmlMigrationBuilder xml(String filename) {

    Pattern pattern = Pattern.compile(filename.replace(".", "\\."));
    return new XmlMigrationBuilder(this, pattern);
  }

  /**
   * @return the parent builder to continue once complete with this migration step.
   */
  public MigrationBuilder next() {

    assert (!this.step.getFileMigrations().isEmpty());
    this.parent.steps.add(this.step);
    return this.parent;
  }

}
