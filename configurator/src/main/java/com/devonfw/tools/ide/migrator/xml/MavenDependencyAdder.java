package com.devonfw.tools.ide.migrator.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Implementation of {@link XmlMigration} for adding a maven dependency.
 */
public class MavenDependencyAdder extends AbstractXmlMigration {

  private final VersionIdentifier projectPattern;

  private final VersionIdentifier dependency;

  /**
   * The constructor.
   *
   * @param projectPattern the {@link VersionIdentifier} of the maven project/module to match.
   * @param dependency the {@link VersionIdentifier} with the dependency to add.
   */
  public MavenDependencyAdder(VersionIdentifier projectPattern, VersionIdentifier dependency) {

    super();
    this.projectPattern = projectPattern;
    this.dependency = dependency;
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    Element projectElement = xml.getDocumentElement();
    Element artifactIdElement = getChildElement(projectElement, "artifactId");
    String artifactId = getText(artifactIdElement);
    Element groupIdElement = getChildElement(projectElement, "groupId");
    String groupId = getText(groupIdElement);
    if (groupId == null) {
      Element parentElement = getChildElement(projectElement, "parent");
      groupIdElement = getChildElement(parentElement, "groupId");
      groupId = getText(groupIdElement);
    }
    Element versionElement = getChildElement(projectElement, "version");
    String version = getText(versionElement);
    VersionIdentifier vi = new VersionIdentifier(groupId, artifactId, version);
    if (this.projectPattern.matches(vi)) {
      Element dependenciesElement = getChildElement(projectElement, "dependencies");
      if (dependenciesElement != null) {
        Element dependencyElement = xml.createElement("dependency");
        Element dependencyGroupIdElement = xml.createElement("groupId");
        dependencyGroupIdElement.setTextContent(this.dependency.getGroupId());
        dependencyElement.appendChild(dependencyGroupIdElement);
        Element dependencyArtifactIdElement = xml.createElement("artifactId");
        dependencyArtifactIdElement.setTextContent(this.dependency.getArtifactId());
        dependencyElement.appendChild(dependencyArtifactIdElement);
        String dependencyVersion = this.dependency.getVersion();
        if (dependencyVersion != null) {
          Element dependencyVersionElement = xml.createElement("version");
          dependencyVersionElement.setTextContent(dependencyVersion);
          dependencyElement.appendChild(dependencyVersionElement);
        }
        String scope = this.dependency.getScope();
        if (scope != null) {
          Element dependencyScopeElement = xml.createElement("scope");
          dependencyScopeElement.setTextContent(scope);
          dependencyElement.appendChild(dependencyScopeElement);
        }
        dependenciesElement.appendChild(dependencyElement);
        return true;
      }
    }
    return false;
  }

}
