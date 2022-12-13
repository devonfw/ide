package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class from which UrlRepository inherits, as its objects don't have a parent, but possibly child objects of the class
 * UrlTool.
 *
 * @param <C> Type of the child object.
 */
public abstract class UrlHasChildArtifact<C extends UrlHasChildParentArtifact<?, ?>> extends UrlArtifact {
  protected Map<String, C> children;

  /**
   * Set the children variable as a hashmap for later use.
   *
   * @param path
   */
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
   * Returns a LinkedList of directories that are inside the directory given by the current objects path. Open for
   * debate if this method is necessary.
   */
  public void getChildrenInDirectory() {

    File[] directories = new File(path.toString()).listFiles(File::isDirectory);
    int l = directories.length;
    // System.out.println(l);
    LinkedList<String> listOfChildrenInDir = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
    }

  }

  protected abstract C newChild(String name);
}
