package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <P> Parent type
 * @param <C> Child type
 */
public abstract class UrlArtifact<P, C> {
	protected final Path path;

	public UrlArtifact(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return this.path;
	}

}
