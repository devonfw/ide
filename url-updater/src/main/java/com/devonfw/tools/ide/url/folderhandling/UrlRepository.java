package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;

/**
 * An instance of this class represents the folder that starts an url-file repository, having UrlTool objects as
 * children.
 *
 */
public class UrlRepository extends UrlHasChildArtifact<UrlTool> {

  Path path;

  /**
   *
   * @param path Enter the path to the url folder structure in which the tool folders are supposed to be later on in the
   *        creation process.
   */
  public UrlRepository(Path path) {

    super(path);
    this.path = path;

  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the tool object that should be created.
   */
  @Override
  protected UrlTool newChild(String name) {

    return new UrlTool(this, name);
  }

}
