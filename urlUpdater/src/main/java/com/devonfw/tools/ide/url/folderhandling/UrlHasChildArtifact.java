package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

	/**
	 * Returns an LinkedList of directories (or for ulrVersion of files) that are inside the
	 * directory given by the current objects path.
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

	protected abstract C newChild(String name);
}
