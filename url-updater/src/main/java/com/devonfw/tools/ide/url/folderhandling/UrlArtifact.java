package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The abstract class from which {@link UrlHasChildArtifact} , {@link UrlHasChildParentArtifact} and {@link UrlHasParentArtifact} and because of
 * that {@link UrlRepository}, {@link UrlTool}, {@link UrlEdition}, {@link UrlVersion} and {@link UrlFile} indirectly inherit.
 *
 * @param <P> Parent type
 * @param <C> Child type
 */
public abstract class UrlArtifact {
  protected final Path path;

  public UrlArtifact(Path path) {

    this.path = path;
  }

  public Path getPath() {

    return this.path;
  }

}
