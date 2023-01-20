package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFolder;

/**
 * An instance of this class represents the folder that starts an url-file
 * repository, having {@link UrlTool} objects as children.
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

	/**
	 * This method is used to add new children to the children collection of an
	 * instance from this class.
	 *
	 * @param name The name of the {@link UrlTool} object that should be created.
	 */
	@Override
	protected UrlTool newChild(String name) {

		return new UrlTool(this, name);
	}

	/**
	 * @param path the {@link #getPath() path} of the {@link UrlRepository} to load.
	 * @return the {@link UrlRepository} with all its children loaded from the given
	 *         {@link Path}.
	 */
	public static UrlRepository load(Path path) {
		UrlRepository repository = new UrlRepository(path);
		repository.load();
		return repository;
	}

}
