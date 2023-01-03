package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * An instance of this class represents a version folder, like version 1.6.2 of rancher-desktop (as edition of the tool
 * docker). It offers a method to create a file named in accordance with the structure discussed in issue #941.
 *
 */
public class UrlVersion extends UrlHasChildParentArtifact<UrlEdition, UrlFile> {

  public UrlVersion(UrlEdition parent, String name) {

    super(parent, name);
  }

  protected void makeFile(String os, String arch) throws IOException {

    Path filePath = getPath().resolve(os + "_" + arch + ".urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  protected void makeFile(String os) throws IOException {

    Path filePath = getPath().resolve(os + ".urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  protected void makeFile() throws IOException {

    Path filePath = getPath().resolve("urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  /**
   * Open to discussion if this method is needed. Differs from the method with similar name in class
   * UrlHasChildParentArtifact by giving out files instead of directories.
   *
   * @return
   */
  @Override
  public List<String> getChildrenInDirectory() {

    File[] directories = new File(path.toString()).listFiles(File::isFile);
    int l = directories.length;
    System.out.println(l);
    LinkedList<String> listOfChildrenInDir = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
      System.out.println(listOfChildrenInDir.get(i));
    }
    return listOfChildrenInDir;
  }

  @Override
  protected UrlFile newChild(String name) {

    return new UrlFile(this, name);
  }
}
