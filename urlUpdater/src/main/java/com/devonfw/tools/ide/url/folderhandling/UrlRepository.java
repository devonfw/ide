package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;

public class UrlRepository extends UrlHasChildArtifact<UrlRepository, UrlTool> {

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

  @Override
  protected UrlTool newChild(String name) {

    return new UrlTool(this, name);
  }

}
