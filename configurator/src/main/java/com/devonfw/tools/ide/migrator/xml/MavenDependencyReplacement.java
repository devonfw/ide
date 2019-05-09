package com.devonfw.tools.ide.migrator.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Implementation of {@link XmlMigration} for replacing a maven dependency.
 */
public class MavenDependencyReplacement extends AbstractXmlMigration {

  private final VersionIdentifier pattern;

  private final VersionIdentifier replacement;

  /**
   * The constructor.
   *
   * @param pattern the {@link VersionIdentifier} to match as pattern.
   * @param replacement the {@link VersionIdentifier} with the replacement.
   */
  public MavenDependencyReplacement(VersionIdentifier pattern, VersionIdentifier replacement) {

    super();
    this.pattern = pattern;
    this.replacement = replacement;
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    boolean changed = false;
    NodeList dependencies = xml.getElementsByTagName("dependency");
    for (int i = 0; i < dependencies.getLength(); i++) {
      Element dependency = (Element) dependencies.item(i);
      boolean modified = migrateDependency(dependency);
      if (modified) {
        changed = true;
      }
    }
    return changed;
  }

  private boolean migrateDependency(Element dependency) {

    Element groupIdElement = getChildElement(dependency, "groupId");
    String groupId = getText(groupIdElement);
    Element artifactIdElement = getChildElement(dependency, "artifactId");
    String artifactId = getText(artifactIdElement);
    Element versionElement = getChildElement(dependency, "version");
    String version = getText(versionElement);
    VersionIdentifier vi = new VersionIdentifier(groupId, artifactId, version);
    if (this.pattern.matches(vi)) {
      replace(groupIdElement, this.replacement.getGroupId(), this.pattern.getGroupId(), groupId);
      replace(artifactIdElement, this.replacement.getArtifactId(), this.pattern.getArtifactId(), artifactId);
      replace(versionElement, this.replacement.getVersion(), this.pattern.getVersion(), version);
      return true;
    }
    return false;
  }

  private void replace(Element element, String replacementString, String patternString, String value) {

    if (value == null) {
      return;
    }
    if ((patternString != null) && (patternString.endsWith("*") && (replacementString != null) && (replacementString.endsWith("*")))) {
      String suffix = value.substring(patternString.length() - 1);
      setText(element, replacementString.substring(0, replacementString.length() - 1) + suffix);
    } else {
      setText(element, replacementString);
    }
  }

}
