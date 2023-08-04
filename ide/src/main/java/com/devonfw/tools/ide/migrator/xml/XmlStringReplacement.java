package com.devonfw.tools.ide.migrator.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Implementation of {@link AbstractXmlMigration} for a simple {@link String} replacement in any
 * {@link Element#getTextContent() text}. Currently no attributes are supported as focus is so far on maven that does
 * not make use of attributes.
 */
public class XmlStringReplacement extends AbstractXmlMigration {

  private String search;

  private String replacement;

  /**
   * The constructor.
   *
   * @param search the {@link String} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   */
  public XmlStringReplacement(String search, String replacement) {

    super();
    this.search = search;
    this.replacement = replacement;
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    return migrateXmlElement(xml.getDocumentElement());
  }

  private boolean migrateXmlElement(Element element) {

    boolean updated = false;
    NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node node = childNodes.item(i);
      short nodeType = node.getNodeType();
      if (nodeType == Node.ELEMENT_NODE) {
        boolean childUpdated = migrateXmlElement((Element) node);
        if (childUpdated) {
          updated = true;
        }
      } else if (nodeType == Node.TEXT_NODE) {
        Text text = (Text) node;
        if (this.search.equals(text.getData())) {
          text.setData(this.replacement);
          updated = true;
        }
      }
    }
    return updated;
  }

}
