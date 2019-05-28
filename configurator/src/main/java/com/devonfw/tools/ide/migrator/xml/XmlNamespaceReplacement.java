package com.devonfw.tools.ide.migrator.xml;

import javax.xml.stream.events.Namespace;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Implementation of {@link AbstractXmlMigration} for a simple {@link Namespace} replacement.
 */
public class XmlNamespaceReplacement extends AbstractXmlMigration {

  private String search;

  private String replacement;

  /**
   * The constructor.
   *
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   */
  public XmlNamespaceReplacement(String search, String replacement) {

    super();
    this.search = search;
    this.replacement = replacement;
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    return migrateXmlElementAttribute(xml.getDocumentElement().getAttributes());
  }

  private boolean migrateXmlElementAttribute(NamedNodeMap childAttr) {

    boolean updated = false;

    for (int i = 0; i < childAttr.getLength(); i++) {
      Node node = childAttr.item(i);
      short nodeType = node.getNodeType();
      if (nodeType == Node.ATTRIBUTE_NODE) {
        if (this.search.equals(node.getNodeValue())) {
          node.setNodeValue(this.replacement);
          updated = true;
        }
      }
    }
    return updated;
  }
}
