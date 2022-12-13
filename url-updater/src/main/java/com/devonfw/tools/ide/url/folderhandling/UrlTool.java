package com.devonfw.tools.ide.url.folderhandling;

/**
 * An {@link UrlFolder} representing the actual software tool like "docker" or "vscode".
 */
public class UrlTool extends AbstractUrlFolderWithParent<UrlRepository, UrlEdition> {

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public UrlTool(UrlRepository parent, String name) {

    super(parent, name);
  }

  @Override
  protected UrlEdition newChild(String name) {

    return new UrlEdition(this, name);
  }

}
