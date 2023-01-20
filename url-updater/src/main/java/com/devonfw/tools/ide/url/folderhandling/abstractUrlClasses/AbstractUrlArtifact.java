package com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses;

import java.nio.file.Path;

import com.devonfw.tools.ide.url.folderhandling.UrlArtifact;

/**
 * Abstract base implementation of {@link UrlArtifact}.
 */
public abstract class AbstractUrlArtifact implements UrlArtifact {

	private final Path path;

	private final String name;

	/**
	 * The constructor.
	 *
	 * @param path the {@link #getPath() path}.
	 * @param name the {@link #getName() filename}.
	 */
	public AbstractUrlArtifact(Path path, String name) {

		super();
		this.path = path;
		this.name = name;
	}

	@Override
	public Path getPath() {

		return this.path;
	}

	@Override
	public String getName() {

		return this.name;
	}

	/**
	 * Loads this artifact from the disc. Will recursively initialized its children.
	 */
	protected abstract void load();

	/**
	 * Saves this artifact to the disc. Will recursively save its children.
	 * Unchanged files remain untouched.
	 */
	public abstract void save();

	@Override
	public String toString() {

		return this.name + "[" + getClass().getSimpleName() + "@" + this.path + "]";
	}

}
