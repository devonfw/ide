package com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses;

import com.devonfw.tools.ide.url.folderhandling.UrlFile;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

/**
 * Abstract base implementation of {@link UrlFile}.
 */
public abstract class AbstractUrlFile extends AbstractUrlArtifactWithParent<UrlVersion> implements UrlFile {


  /**
   * {@code true} if modified and changes are unsaved, {@code false} otherwise.
   */
  protected boolean modified;

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public AbstractUrlFile(UrlVersion parent, String name) {

    super(parent, name);
    this.modified = true;
  }

  @Override
  protected void load() {

    doLoad();
    this.modified = false;
  }

  /**
   * Performs the actual loading.
   *
   * @see #load()
   */
  protected abstract void doLoad();

  @Override
  public void save() {

    if (this.modified) {
      doSave();
      this.modified = false;
    }
  }

  /**
   * Performs the actual saving.
   *
   * @see #save()
   */
  protected abstract void doSave();

}
