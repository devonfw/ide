package com.devonfw.tools.ide.migrator.xml;

import java.util.regex.Pattern;

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
public class XmlRegexReplacement extends AbstractXmlMigration {

  private Pattern pattern;

  private String replacement;

  /**
   * The constructor.
   *
   * @param pattern the {@link Pattern} to search for.
   * @param replacement the replacement for the given {@code search} {@link String}.
   */
  public XmlRegexReplacement(Pattern pattern, String replacement) {

    super();
    this.pattern = pattern;
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
        String data = text.getData();
        String newData = data.replaceAll(this.pattern.pattern(), this.replacement);
        if (!data.equals(newData)) {
          text.setData(newData);
          updated = true;
        }
      }
    }
    return updated;
  }

}
