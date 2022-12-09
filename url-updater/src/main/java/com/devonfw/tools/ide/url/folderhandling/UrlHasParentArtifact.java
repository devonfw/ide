package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;
import java.util.Map;

/**
 * Class from which UrlFile inherits, as its instances each have a parent, but no children.
 * It definies a method for getting parent objects and the name variable.
 *
 * @param <P> The parent object to use.
 */
public abstract class UrlHasParentArtifact<P extends UrlArtifact> extends UrlArtifact {
	protected final P parent;
	protected final String name;

	public UrlHasParentArtifact(P parent, String name) {
		super(parent.getPath().resolve(name));
		this.parent = parent;
		this.name = name;
	}

	public P getParent() {

		return this.parent;
	}

	public String getName() {

		return this.name;
	}

}
