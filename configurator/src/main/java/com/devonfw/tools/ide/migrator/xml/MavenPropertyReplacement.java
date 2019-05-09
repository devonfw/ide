package com.devonfw.tools.ide.migrator.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Implementation of {@link XmlMigration} for replacing a maven property.
 */
public class MavenPropertyReplacement extends AbstractXmlMigration {

  private final String propertyName;

  private final String newPropertyName;

  private final String newValue;

  /**
   * The constructor.
   *
   * @param propertyName the name of the property to modify.
   * @param newValue the new value of the specified property.
   */
  public MavenPropertyReplacement(String propertyName, String newValue) {

    this(propertyName, newValue, propertyName);
  }

  /**
   * The constructor.
   *
   * @param propertyName the name of the property to replace.
   * @param newValue the new value of the specified property.
   * @param newPropertyName the new name of the property.
   */
  public MavenPropertyReplacement(String propertyName, String newValue, String newPropertyName) {

    super();
    this.propertyName = propertyName;
    this.newPropertyName = newPropertyName;
    this.newValue = newValue;
  }

  @Override
  public boolean migrateXml(Document xml) throws Exception {

    boolean modified = false;
    Element projectElement = xml.getDocumentElement();
    NodeList propertiesElementList = projectElement.getElementsByTagName("properties");
    for (int i = 0; i < propertiesElementList.getLength(); i++) {
      Element propertiesElement = (Element) propertiesElementList.item(i);
      Element propertyElement = getChildElement(propertiesElement, this.propertyName);
      if (propertyElement != null) {
        modified = true;
        if (this.newPropertyName.equals(this.propertyName)) {
          propertyElement.setTextContent(this.newValue);
        } else {
          Element newProperty = xml.createElement(this.newPropertyName);
          if (this.newValue == null) {
            newProperty.setTextContent(propertyElement.getTextContent());
          } else {
            newProperty.setTextContent(this.newValue);
          }
          propertiesElement.replaceChild(newProperty, propertyElement);
        }
      }
    }
    return modified;
  }

}
