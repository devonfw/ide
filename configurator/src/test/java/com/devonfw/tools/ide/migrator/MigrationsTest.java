package com.devonfw.tools.ide.migrator;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.devonfw.tools.ide.migrator.file.FileMigration;
import com.devonfw.tools.ide.migrator.file.XmlFileMigration;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;
import com.devonfw.tools.ide.migrator.xml.MavenPropertyReplacement;
import com.devonfw.tools.ide.migrator.xml.XmlMigration;

/**
 * Test of {@link Migrations}.
 */
public class MigrationsTest extends Assertions {

  @Test
  public void testMigrations() throws Exception {

    MigrationImpl devon4j = Migrations.devon4j();
    List<VersionIdentifier> oasp4jVersions = Arrays.asList(VersionIdentifier.ofOasp4j("2.6.0"),
        VersionIdentifier.ofOasp4j("2.6.1"), VersionIdentifier.ofOasp4j("3.0.0"));
    List<VersionIdentifier> versions = new ArrayList<>(oasp4jVersions);
    List<String> devon4jVersions = determineDevon4jVersions();
    assertThat(devon4jVersions.size()).isGreaterThan(3);
    for (String devon4jVersion : devon4jVersions) {
      versions.add(VersionIdentifier.ofDevon4j(devon4jVersion));
    }
    MigrationStep step;
    VersionIdentifier current = null;
    int versionIndexMax = versions.size() - 1;
    for (int versionIndex = 0; versionIndex <= versionIndexMax; versionIndex++) {
      current = versions.get(versionIndex);
      step = devon4j.get(current);
      if (versionIndex < versionIndexMax) {
        assertThat(step).isNotNull();
      }
      if (step != null) {
        assertThat(step.getFrom()).isEqualTo(current);
        assertThat(step.getTo()).isEqualTo(versions.get(versionIndex + 1));
        List<FileMigration> fileMigrations = ((MigrationStepImpl) step).getFileMigrations();
        assertThat(fileMigrations).isNotEmpty();
        FileMigration versionUpgrade = fileMigrations.get(0);
        assertThat(versionUpgrade.getNamePattern()).isSameAs(XmlFileMigration.POM_XML_PATTERN);
        assertThat(versionUpgrade).isInstanceOf(XmlFileMigration.class);
        List<XmlMigration> versionMigrations = ((XmlFileMigration) versionUpgrade).getMigrations();
        assertThat(versionMigrations).isNotEmpty();
        XmlMigration xmlMigration = versionMigrations.get(0);
        assertThat(xmlMigration).isInstanceOf(MavenPropertyReplacement.class);
        MavenPropertyReplacement mavenPropertyReplacement = (MavenPropertyReplacement) xmlMigration;
        if (step.getFrom().getArtifactId().equals("devon4j")) {
          assertThat(mavenPropertyReplacement.getPropertyName()).isEqualTo("devon4j.version");
        } else {
          assertThat(mavenPropertyReplacement.getPropertyName()).isEqualTo("oasp4j.version");
        }
        assertThat(mavenPropertyReplacement.getNewValue()).isEqualTo(step.getTo().getVersion());
      }
    }
  }

  private List<String> determineDevon4jVersions()
      throws IOException, MalformedURLException, SAXException, ParserConfigurationException {

    List<String> versions = new ArrayList<>();
    String url = "https://repo1.maven.org/maven2/com/devonfw/java/modules/devon4j-basic/maven-metadata.xml";
    InputStream in = new URL(url).openStream();
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
    NodeList versionElementList = doc.getElementsByTagName("version");
    for (int i = 0; i < versionElementList.getLength(); i++) {
      Element element = (Element) versionElementList.item(i);
      String version = element.getTextContent().trim();
      if (!version.contains("-alpha") && !version.contains("-beta")) {
        versions.add(version);
      }
    }
    return versions;
  }

}
