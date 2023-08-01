package com.devonfw.tools.ide.migrator.xml;

/**
 * Base class for implementations of {@link XmlMigration}.
 */
public abstract class AbstractXmlMigration extends AbstractXmlSupport implements XmlMigration {

  /**
   * Constant for single space
   */
  public static final String SINGLE_SPACE = " ";

  /**
   * @param str the {@link String} to trim.
   * @return trim and single space string
   */
  public static String removeSpaces(String str) {

    return str.trim().replaceAll("\\s+", SINGLE_SPACE);
  }
}
