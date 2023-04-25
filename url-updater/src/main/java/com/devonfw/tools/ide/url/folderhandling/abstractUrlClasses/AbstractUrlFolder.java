package com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses;

import com.devonfw.tools.ide.url.folderhandling.UrlArtifactWithParent;
import com.devonfw.tools.ide.url.folderhandling.UrlFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class from which UrlRepository inherits, as its objects don't have a parent, but possibly child objects of the class
 * UrlTool.
 *
 * @param <C> Type of the child object.
 */
public abstract class AbstractUrlFolder<C extends UrlArtifactWithParent<?>> extends AbstractUrlArtifact implements UrlFolder<C> {

	private final Map<String, C> children;

	/**
	 * The constructor.
	 *
	 * @param path the {@link #getPath() path}.
	 * @param name the {@link #getName() filename}.
	 */
	public AbstractUrlFolder(Path path, String name) {
		super(path, name);
		this.children = new HashMap<>();
	}

	@Override
	public int getChildCount() {
		return this.children.size();
	}

	@Override
	public C getChild(String name) {
		return this.children.get(name);
	}

	@Override
	public C getOrCreateChild(String name) {
		return this.children.computeIfAbsent(name, p -> newChild(name));
	}

	@Override
	public List<C> getChildren() {
		return new ArrayList<>(this.children.values());
	}

  /**
   * @param name the plain filename (excluding any path).
   * @param folder - {@code true} in case of a folder, {@code false} otherwise (plain data file).
   * @return {@code true} if the existing file from the file-system should be {@link #getOrCreateChild(String) created
   *         as child}, {@code false} otherwise (ignore the file).
   */
  protected boolean isAllowedChild(String name, boolean folder) {

    return folder;
  }


	/**
	 * @return the {@link Set} with all {@link #getName() names} of the children.
	 * @deprecated method name does not make sense here and the method exposes internal mutable structures allowing to
	 * remove or add items to the internal map bypassing the API.
	 */
	@Deprecated
	public Set<String> getAllVersions() {
		return this.children.keySet();
	}

	@Override
	protected void load() {
		Path path = getPath();
		try (Stream<Path> childStream = Files.list(path)) {
			childStream.forEach(this::loadChild);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to list children of directory " + path, e);
		}
	}

  private void loadChild(Path childPath) {

    String name = childPath.getFileName().toString();
    if (name.startsWith(".")) {
      return; // ignore hidden files and folders (e.g. ".git")
    }
    boolean folder = Files.isDirectory(childPath);
    if (isAllowedChild(name, folder)) {
      C child = getOrCreateChild(name);
      ((AbstractUrlArtifact) child).load();
    }
  }

	/**
	 * @param name the {@link #getName() name} of the requested child.
	 * @return the new child object.
	 */
	protected abstract C newChild(String name);

	@Override
	public void save() {
		for (C child : this.children.values()) {
			((AbstractUrlArtifact) child).save();
		}
	}
}
