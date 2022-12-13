package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;

/**
 * Allows to load an already existing download-urls folder structure by giving on an object of type UrlRepository,
 * indicating where the folder structure begins.
 *
 * @deprecated
 */
@Deprecated
public class UrlReader<O extends AbstractUrlArtifact> {

  /**
   *
   * @param UrlRepoObject The UrlRepository object representing the folder to start loading the folder structure for the
   *        download-urls.
   */
  public <O extends UrlFolder<?>> void readFolderStructure(O urlObject) {

    File dirPath = new File(urlObject.getPath().toString());
    String contents[] = dirPath.list();

    if (contents != null) {
      for (String itemName : contents) {

        // TO DO: Add a filter, so that config-files won't cause object creation!
        UrlArtifactWithParent<?> child = urlObject.getOrCreateChild(itemName);
        if (child instanceof UrlFolder<?>) {
          readFolderStructure((UrlFolder<?>) child);
        }
      }
    }
  }

}