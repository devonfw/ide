package com.devonfw.tools.ide.url.folderhandling;

/**
 *
 * An instance of this class represents a tool folder, like "docker" or "vscode".
 *
 */
public class UrlTool extends UrlHasChildParentArtifact<UrlRepository, UrlEdition> {

  public UrlTool(UrlRepository parent, String name) {

    super(parent, name);
  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the {@link UrlEdition} object that should be created.
   */
  @Override
  protected UrlEdition newChild(String name) {

    return new UrlEdition(this, name);
  }

}
