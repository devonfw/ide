package com.devonfw.tools.ide.migrator.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.devonfw.tools.ide.logging.Output;
import com.devonfw.tools.ide.migrator.xml.XmlMigration;

/**
 * Implementation of {@link FileMigration} for XML {@link File}.
 */
public class XmlFileMigration extends FileMigration implements XmlMigration {

  /** {@link Pattern} for {@code pom.xml}. */
  public static final Pattern POM_XML_PATTERN = Pattern.compile("pom\\.xml");

  private final List<XmlMigration> migrations;

  /**
   * The constructor.
   *
   * @param namePattern the {@link Pattern} to match the filename.
   */
  public XmlFileMigration(Pattern namePattern) {

    super(namePattern);
    this.migrations = new ArrayList<>();
  }

  @Override
  protected void migrateFile(File file) throws Exception {

    Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    boolean change = migrateXml(xml);
    if (change) {
      Output.get().info("Migrating file: %s", file.getPath());
      Result target = new StreamResult(file);
      TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xml), target);
    }
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    boolean change = false;
    for (XmlMigration migration : this.migrations) {
      boolean changed = migration.migrateXml(xml);
      if (changed) {
        change = true;
      }
    }
    return change;
  }

  /**
   * @return migrations
   */
  public List<XmlMigration> getMigrations() {

    return this.migrations;
  }

}
