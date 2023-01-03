package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 *
 * Class from which UrlTool, UrlEdition and UrlVersion inherit, as their objects have both parent and eventually child
 * objects. It mainly gives methods to read a parent object or to create or read child objects.
 *
 * @param <P> Type of the parent object
 * @param <C> Type of the child objects
 */
public abstract class UrlHasChildParentArtifact<P extends UrlArtifact, C extends UrlArtifact> extends UrlArtifact {
  protected final P parent;

  protected final String name;

  protected Map<String, C> children;

  /**
   * Set the path variable of the instance by adding the name variable to the instances parent path. Set the parent and
   * name variable and set the children variable as a hashmap for later use.
   *
   * @param parent Parent object to be used
   * @param name The name of the child, e.g. if of the child is of type UrlEdition the name could be rancher-desktop, if
   *        the object of type UrlTool has docker as name.
   */
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
   * The method is supposed to give the current folders content, independently of the current child structure, that may
   * differ from the folders content, due to manipulation but not yet saved changes. It is open for debate if this
   * method is necessary, as it was used for the structures development and may not be needed later on.
   */
  public List<String> getChildrenInDirectory() {
    //Get all directory names in current directory as List of Strings
    File[] directories = new File(path.toString()).listFiles(File::isDirectory);
    ArrayList<String> listOfChildrenInDir = new ArrayList<>();
    if(directories != null) {
      for (File directory : directories) {
        listOfChildrenInDir.add(directory.getName());
      }
    }
    return listOfChildrenInDir;

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