package com.devonfw.tools.ide.url.folderhandling;

/**
 * {@link UrlFile} for the "status.json" file.
 */
public class UrlStatusFile extends AbstractUrlFile {

  /** Constant {@link UrlStatusFile#getName() filename}. */
  static final String STATUS_JSON = "status.json";

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   */
  public UrlStatusFile(UrlVersion parent) {

    super(parent, STATUS_JSON);
  }

  @Override
  protected void doLoad() {

  }

  @Override
  protected void doSave() {

  }

}
