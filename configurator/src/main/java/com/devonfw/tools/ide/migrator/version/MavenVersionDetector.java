package com.devonfw.tools.ide.migrator.version;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.devonfw.tools.ide.migrator.xml.AbstractXmlSupport;

/**
 * Implementation of {@link VersionDetector} for Maven projects based on {@code pom.xml} {@link File}.
 */
public class MavenVersionDetector extends AbstractXmlSupport implements VersionDetector {

  @Override
  public VersionIdentifier detectVersion(File projectFolder) throws Exception {

    File pom = new File(projectFolder, "pom.xml");
    if (!pom.exists()) {
      throw new IllegalArgumentException("No pom.xml found in project folder: " + projectFolder);
    }
    Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pom);
    Element properties = getChildElement(xml.getDocumentElement(), "properties");
    Element version = getChildElement(properties, "devon4j.version");
    if (version != null) {
      return VersionIdentifier.ofDevon4j(version.getTextContent().trim());
    }
    version = getChildElement(properties, "oasp4j.version");
    if (version != null) {
      return VersionIdentifier.ofOasp4j(version.getTextContent().trim());
    }
    throw new IllegalArgumentException("Could not determine version of oasp4j or devon4j from pom.xml: " + pom);
  }

}
