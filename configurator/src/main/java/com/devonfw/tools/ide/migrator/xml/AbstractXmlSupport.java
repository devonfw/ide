package com.devonfw.tools.ide.migrator.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Base class providing simple XML DOM functionality.
 */
public abstract class AbstractXmlSupport {

  /**
   * @param element the {@link Element} to modify.
   * @param text the new {@link Element#setTextContent(String) text content}.
   */
  protected void setText(Element element, String text) {

    if ((element != null) && (text != null)) {
      element.setTextContent(text);
    }
  }

  /**
   * @param element the {@link Element} to read from.
   * @return the {@link Element#getTextContent() text content} of the given {@link Element}.
   */
  protected String getText(Element element) {

    if (element == null) {
      return null;
    }
    return element.getTextContent();
  }

  /**
   * @param parent the {@link Element} to read from.
   * @param tag the {@link Element#getTagName() tag name} of the requested child.
   * @return the (first) child of the given {@link Element} with the given tag.
   */
  protected Element getChildElement(Element parent, String tag) {

    if (parent == null) {
      return null;
    }
    NodeList childNodes = parent.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node node = childNodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        if (element.getTagName().equals(tag)) {
          return element;
        }
      }
    }
    return null;
  }

}
