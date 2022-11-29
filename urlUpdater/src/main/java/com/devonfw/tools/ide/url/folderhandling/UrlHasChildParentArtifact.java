package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
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

	/**
	 * Die Methode dient dazu die tatsächlich vorhandenen Ordner und damit
	 * bspw. auch Versionen zu erhalten, unabhängig von der ggf. bereits manipulierten
	 * Kindstruktur des aktuellen Objekts.
	 */
	public void getChildrenInDirectory() {
		File[] directories = new File(path.toString()).listFiles(File::isDirectory);
		int l = directories.length;
		System.out.println(l);
		LinkedList<String> listOfChildrenInDir = new LinkedList<>();
		for (int i=0; i<l; i++) {
			listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
			System.out.println(listOfChildrenInDir.get(i));
		}
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
