package com.devonfw.tools.ide.url.folderhandling;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlArtifact;

/**
 * Interface for an {@link UrlArtifact} representing a folder containing other
 * {@link UrlArtifact artifacts} as children.
 */
public interface UrlFolder<C extends UrlArtifactWithParent> extends UrlArtifact {

	/**
	 * @return the (current) number of {@link #getChild(String) children} contained
	 *         in this folder.
	 */
	int getChildCount();

	/**
	 * @param name the {@link #getName() name} of the requested child.
	 * @return the child {@link AbstractUrlArtifact artifact} with the given
	 *         {@link #getName() name} or {@code null} if not found.
	 */
	C getChild(String name);

	/**
	 * @param name the {@link #getName() name} of the requested child.
	 * @return the child {@link AbstractUrlArtifact artifact} with the given
	 *         {@link #getName() name}. Will be created if it does not already
	 *         exist.
	 */
	C getOrCreateChild(String name);

}
