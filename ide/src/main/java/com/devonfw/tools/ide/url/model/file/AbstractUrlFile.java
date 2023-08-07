package com.devonfw.tools.ide.url.model.file;

import com.devonfw.tools.ide.url.model.AbstractUrlArtifactWithParent;
import com.devonfw.tools.ide.url.model.folder.AbstractUrlFolder;
import com.devonfw.tools.ide.url.model.folder.UrlFolder;

/**
 * Abstract base implementation of {@link UrlFile}.
 *
 * @param <P> type of the {@link #getParent() parent} {@link UrlFolder folder}.
 */
public abstract class AbstractUrlFile<P extends AbstractUrlFolder<?>> extends AbstractUrlArtifactWithParent<P>
    implements UrlFile<P> {

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
  public AbstractUrlFile(P parent, String name) {

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
