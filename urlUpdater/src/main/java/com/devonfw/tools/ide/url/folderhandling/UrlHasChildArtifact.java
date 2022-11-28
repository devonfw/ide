package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class UrlHasChildArtifact<P ,C> extends UrlArtifact<P,C> {
	protected Map<String, C> children;


	public UrlHasChildArtifact(Path path) {
		super(path);
		this.children = new HashMap<>();
	}


	public int getChildCount() {

	    return this.children.size();
	}

	public C getChild(String name) {

	    return this.children.get(name);
	}

	public C getOrCreateChild(String name) {

	    return this.children.computeIfAbsent(name, p -> newChild(name));
	}


	protected abstract C newChild(String name);
}
