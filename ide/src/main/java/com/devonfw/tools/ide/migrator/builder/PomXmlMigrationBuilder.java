package com.devonfw.tools.ide.migrator.builder;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.file.XmlFileMigration;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;
import com.devonfw.tools.ide.migrator.xml.MavenDependencyAdder;
import com.devonfw.tools.ide.migrator.xml.MavenDependencyReplacement;
import com.devonfw.tools.ide.migrator.xml.MavenPropertyReplacement;
import com.devonfw.tools.ide.migrator.xml.XmlRegexReplacement;
import com.devonfw.tools.ide.migrator.xml.XmlStringReplacement;

/**
 * {@link XmlMigrationBuilder} for POM files.
 */
public class PomXmlMigrationBuilder extends XmlMigrationBuilder {

  /**
   * The constructor.
   *
   * @param parent the parent builder.
   */
  public PomXmlMigrationBuilder(MigrationStepBuilder parent) {

    super(parent, XmlFileMigration.POM_XML_PATTERN);
  }

  /**
   * @param pattern the {@link VersionIdentifier} for the dependency to match and replace.
   * @param replacement the {@link VersionIdentifier} with the replacement dependency.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceDependency(VersionIdentifier pattern, VersionIdentifier replacement) {

    this.migration.getMigrations().add(new MavenDependencyReplacement(pattern, replacement));
    return this;
  }

  /**
   * @param projectPattern the {@link VersionIdentifier} of the maven project/module to match.
   * @param dependency the {@link VersionIdentifier} with the dependency to add.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder addDependency(VersionIdentifier projectPattern, VersionIdentifier dependency) {

    this.migration.getMigrations().add(new MavenDependencyAdder(projectPattern, dependency));
    return this;
  }

  /**
   * @param property the name of the Maven property to replace.
   * @param value the new value of the specified property.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceProperty(String property, String value) {

    return replaceProperty(property, value, property);
  }

  /**
   * @param property the name of the Maven property to replace.
   * @param value the new value of the specified property.
   * @param newProperty the new name of the Maven property to replace.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceProperty(String property, String value, String newProperty) {

    this.migration.getMigrations().add(new MavenPropertyReplacement(property, value, newProperty));
    return this;
  }

  /**
   * @param search the plain {@link String} to replace in POM.
   * @param replacement the replacement {@link String}.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceString(String search, String replacement) {

    this.migration.getMigrations().add(new XmlStringReplacement(search, replacement));
    return this;
  }

  /**
   * @param pattern the plain {@link Pattern} to replace in POM.
   * @param replacement the replacement {@link String}.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceRegex(String pattern, String replacement) {

    return replaceRegex(Pattern.compile(pattern), replacement);
  }

  /**
   * @param pattern the plain {@link Pattern} to replace in POM.
   * @param replacement the replacement {@link String}.
   * @return {@code this}.
   */
  public PomXmlMigrationBuilder replaceRegex(Pattern pattern, String replacement) {

    this.migration.getMigrations().add(new XmlRegexReplacement(pattern, replacement));
    return this;
  }

}
