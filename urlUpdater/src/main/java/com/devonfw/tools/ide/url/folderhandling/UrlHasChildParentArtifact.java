package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class UrlHasChildParentArtifact<P extends UrlArtifact<?, ?>, C> extends UrlArtifact<P, C> {
	protected final P parent;

	protected final String name;

	protected Map<String, C> children;


	public UrlHasChildParentArtifact(P parent, String name) {
		super(parent.getPath().resolve(name));
		this.parent = parent;
		this.name = name;
		this.children = new HashMap<>();

	}

	public P getParent() {

		return this.parent;
	}

	public String getName() {

		return this.name;
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
