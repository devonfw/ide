package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UrlVersion extends UrlHasChildParentArtifact<UrlEdition, UrlFile> {

	public UrlVersion(UrlEdition parent, String name) {

		super(parent, name);
	}

	protected void makeFile(String os, String arch) throws IOException {

		Path filePath = getPath().resolve(os + "_" + arch + ".urls");
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
	}

	protected void makeFile(String os) throws IOException {

		Path filePath = getPath().resolve(os + ".urls");
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
	}

	protected void makeFile() throws IOException {

		Path filePath = getPath().resolve("urls");
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
	}

	@Override
	protected UrlFile newChild(String name) {

		return new UrlFile(this, name);
	}
}
