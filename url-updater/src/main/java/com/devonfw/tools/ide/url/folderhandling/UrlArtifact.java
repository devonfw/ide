package com.devonfw.tools.ide.url.folderhandling;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlArtifact;

import java.nio.file.Path;

/**
 * An {@link UrlArtifact} represents a file or folder in the directory structure of a devonfw-ide urls repository.
 *
 * @see UrlRepository
 * @see UrlTool
 * @see UrlEdition
 * @see UrlVersion
 * @see UrlDownloadFile
 */
public interface UrlArtifact {

	/**
	 * @return the {@link Path} to this {@link AbstractUrlArtifact} as folder or file on the disc.
	 */
	Path getPath();

	/**
	 * @return name the file-name of this {@link AbstractUrlArtifact}.
	 */
	String getName();
}
