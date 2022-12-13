package com.devonfw.tools.ide.url.folderhandling;

/**
 * An {@link UrlFolder} representing the actual edition of a {@link UrlTool}. The default edition may have the same
 * {@link #getName() name} as the {@link UrlTool} itself. However, tools like "intellij" may have editions like
 * "community" or "ultimate".
 */
public class UrlEdition extends AbstractUrlFolderWithParent<UrlTool, UrlVersion> {

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public UrlEdition(UrlTool parent, String name) {

    super(parent, name);
  }

  @Override
  protected UrlVersion newChild(String name) {

    return new UrlVersion(this, name);
  }
}
