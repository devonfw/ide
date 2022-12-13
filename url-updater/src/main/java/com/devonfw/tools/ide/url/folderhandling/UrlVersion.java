package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * An {@link UrlFolder} representing the actual version of an {@link UrlEdition}. Examples for the {@link #getName()
 * name} of such version could be "1.6.2" or "17.0.5_8".
 */
public class UrlVersion extends AbstractUrlFolderWithParent<UrlEdition, UrlFile> {

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
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
   * @deprecated
   */
  @Deprecated
  @Override
  public void getChildrenInDirectory() {

    File[] directories = new File(getPath().toString()).listFiles(File::isFile);
    int l = directories.length;
    System.out.println(l);
    LinkedList<String> listOfChildrenInDir = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      listOfChildrenInDir.add(directories[i].toPath().getFileName().toString());
      System.out.println(listOfChildrenInDir.get(i));
    }
  }

  public UrlDownloadFile getOrCreateUrls() {

    return (UrlDownloadFile) getOrCreateChild("urls");
  }

  public UrlDownloadFile getOrCreateUrls(String os, String arch) {

    return (UrlDownloadFile) getOrCreateChild(os + "_" + arch + ".urls");
  }

  public UrlDownloadFile getOrCreateUrls(String os) {

    return (UrlDownloadFile) getOrCreateChild(os + ".urls");
  }

  /**
   * @return the {@link UrlStatusFile}.
   */
  public UrlStatusFile getOrCreateStatus() {

    return (UrlStatusFile) getOrCreateChild(UrlStatusFile.STATUS_JSON);
  }

  @Override
  protected UrlFile newChild(String name) {

    if (UrlStatusFile.STATUS_JSON.equals(name)) {
      return new UrlStatusFile(this);
    }
    return new UrlDownloadFile(this, name);
  }
}
