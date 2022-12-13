package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;

/**
 * An instance of this class represents the folder that starts an url-file repository, having UrlTool objects as
 * children.
 *
 */
public class UrlRepository extends AbstractUrlFolder<UrlTool> {

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public UrlRepository(Path path) {

    super(path, "urls");
  }

  @Override
  protected UrlTool newChild(String name) {

    return new UrlTool(this, name);
  }

  /**
   * @param path the {@link #getPath() path} of the {@link UrlRepository} to load.
   * @return the {@link UrlRepository} with all its children loaded from the given {@link Path}.
   */
  public static UrlRepository load(Path path) {

    UrlRepository repository = new UrlRepository(path);
    repository.load();
    return repository;
  }

}
