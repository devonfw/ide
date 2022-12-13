package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;

/**
 * The abstract class from which UrlHasChildArtifact, UrlHasChildParentArtifact and UrlHasParentArtifact and because of
 * that UrlRepository, UrlTool, UrlEdition, UrlVersion and UrlFile indirectly inherit.
 *
 * @param <P> Parent type
 * @param <C> Child type
 */
public abstract class UrlArtifact {
  private final Path path;

  public UrlArtifact(Path path) {

    this.path = path;
  }

  public Path getPath() {

    return this.path;
  }

}
