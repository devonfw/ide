package com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses;

import java.io.File;
import java.util.LinkedList;

import com.devonfw.tools.ide.url.folderhandling.UrlArtifactWithParent;

/**
 *
 * Class from which UrlTool, UrlEdition and UrlVersion inherit, as their objects have both parent and eventually child
 * objects. It mainly gives methods to read a parent object or to create or read child objects.
 *
 * @param <P> Type of the parent object
 * @param <C> Type of the child objects
 */
public abstract class AbstractUrlFolderWithParent<P extends AbstractUrlFolder<?>, C extends UrlArtifactWithParent<?>>
    extends AbstractUrlFolder<C> implements UrlArtifactWithParent<P> {

  private final P parent;

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public AbstractUrlFolderWithParent(P parent, String name) {

    super(parent.getPath().resolve(name), name);
    this.parent = parent;
  }

  @Override
  public P getParent() {

    return this.parent;
  }

  /**
   * The method is supposed to give the current folders content, independently of the current child structure, that may
   * differ from the folders content, due to manipulation but not yet saved changes. It is open for debate if this
   * method is necessary, as it was used for the structures development and may not be needed later on.
   *
   * @deprecated
   */
  @Deprecated
  @Override
  public void getChildrenInDirectory() {

    File[] directories = new File(getPath().toString()).listFiles(File::isDirectory);
    int l = directories.length;
    LinkedList<String> listOfChildrenInDir = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
    }
  }
}
