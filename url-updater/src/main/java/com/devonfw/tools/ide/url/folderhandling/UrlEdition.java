package com.devonfw.tools.ide.url.folderhandling;

/**
 *
 * An instance of this class represents an edition folder. If there are no editions of a tool, then a folder with the
 * tools name should be created. Also the edition level is used in special cases, where the edition level is used to
 * offer multiple software enabling docker. This special case (possibly amongst others) occurs for the tool docker,
 * where rancher-desktop and docker-desktop are represented by two different {@link UrlEdition} objects.
 *
 */
public class UrlEdition extends UrlHasChildParentArtifact<UrlTool, UrlVersion> {

  public UrlEdition(UrlTool parent, String name) {

    super(parent, name);
  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the {@link UrlVersion} object that should be created.
   */
  @Override
  protected UrlVersion newChild(String name) {

    return new UrlVersion(this, name);
  }
}
