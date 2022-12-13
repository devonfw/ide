package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;

/**
 * Allows to load an already existing download-urls folder structure by giving on an object of type UrlRepository,
 * indicating where the folder structure begins.
 */
public class UrlReader<O extends UrlArtifact> {

  /**
   *
   * @param UrlRepoObject The UrlRepository object representing the folder to start loading the folder structure for the
   *        download-urls.
   */
  public <O extends UrlHasChildArtifact> void readFolderStructure(O urlObject) {

    File dirPath = new File(urlObject.getPath().toString());
    String contents[] = dirPath.list();

    if (contents != null) {
      for (String itemName : contents) {

        // TO DO: Add a filter, so that config-files won't cause object creation!
        if (urlObject.getOrCreateChild(itemName) instanceof UrlHasChildParentArtifact<?, ?>) {
          UrlHasChildParentArtifact UrlChildObject = urlObject.getOrCreateChild(itemName);
          readFolderStructure(UrlChildObject);
        }
      }
    }
  }

  public <O extends UrlHasChildParentArtifact<?, ?>> void readFolderStructure(O urlObject) {

    File dirPath = new File(urlObject.getPath().toString());
    String contents[] = dirPath.list();

    for (String itemName : contents) {

      // TO DO: Add a filter, so that config-files won't cause object creation!
      if (urlObject.getOrCreateChild(itemName) instanceof UrlHasChildParentArtifact<?, ?>) {
        UrlHasChildParentArtifact UrlChildObject = (UrlHasChildParentArtifact) urlObject.getOrCreateChild(itemName);
        readFolderStructure(UrlChildObject);
      }
    }
  }
}