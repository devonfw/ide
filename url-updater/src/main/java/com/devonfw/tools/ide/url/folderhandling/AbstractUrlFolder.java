package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Class from which UrlRepository inherits, as its objects don't have a parent, but possibly child objects of the class
 * UrlTool.
 *
 * @param <C> Type of the child object.
 */
public abstract class AbstractUrlFolder<C extends UrlArtifactWithParent<?>> extends AbstractUrlArtifact
    implements UrlFolder<C> {

  private Map<String, C> children;

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

  /**
   * Returns a LinkedList of directories that are inside the directory given by the current objects path. Open for
   * debate if this method is necessary.
   *
   * @deprecated
   */
  @Deprecated
  public void getChildrenInDirectory() {

    File[] directories = new File(getPath().toString()).listFiles(File::isDirectory);
    int l = directories.length;
    // System.out.println(l);
    LinkedList<String> listOfChildrenInDir = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
    }

  }

  /**
   * @param name the {@link #getName() name} of the requested child.
   * @return the new child object.
   */
  protected abstract C newChild(String name);

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
    if (!name.startsWith(".")) {
      C child = getOrCreateChild(name);
      ((AbstractUrlArtifact) child).load();
    }
  }

  @Override
  protected void save() {

    for (C child : this.children.values()) {
      ((AbstractUrlArtifact) child).save();
    }
  }
}
