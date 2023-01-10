package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFolderWithParent;

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

  /**
   * @deprecated use {@link #getOrCreateUrls(String, String)}
   */
  @Deprecated
  protected void makeFile(String os, String arch) throws IOException {

    Path filePath = getPath().resolve(os + "_" + arch + ".urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  /**
   * @deprecated use {@link #getOrCreateUrls(String)}
   */
  @Deprecated
  protected void makeFile(String os) throws IOException {

    Path filePath = getPath().resolve(os + ".urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  /**
   * @deprecated use {@link #getOrCreateUrls()}
   */
  @Deprecated
  protected void makeFile() throws IOException {

    Path filePath = getPath().resolve("urls");
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }
  }

  /**
   * @return the {@link UrlDownloadFile} {@link #getName() named} "urls". Will be created if it does not exist.
   */
  public UrlDownloadFile getOrCreateUrls() {

    return (UrlDownloadFile) getOrCreateChild("urls");
  }

  /**
   * @param os the name of the operating system ("windows", "linux", "mac").
   * @return the {@link UrlDownloadFile} {@link #getName() named} "«os».urls". Will be created if it does not exist.
   */
  public UrlDownloadFile getOrCreateUrls(String os) {
    return  getOrCreateUrls(os, null);
  }

  /**
   * @param os the name of the operating system ("windows", "linux", "mac").
   * @param arch the architecture (e.g. "x64" or "arm64").
   * @return the {@link UrlDownloadFile} {@link #getName() named} "«os»_«arch».urls". Will be created if it does not
   *         exist.
   */
  public UrlDownloadFile getOrCreateUrls(String os, String arch) {
    if(arch== null){
      arch = "x64";
    }
    return (UrlDownloadFile) getOrCreateChild(os + "_" + arch + ".urls");
  }

  /**
   * @return the {@link UrlStatusFile}.
   */
  public UrlStatusFile getOrCreateStatus() {

    return (UrlStatusFile) getOrCreateChild(UrlStatusFile.STATUS_JSON);
  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the {@link UrlFile} object that should be created.
   */
  @Override
  protected UrlFile newChild(String name) {

    if (UrlStatusFile.STATUS_JSON.equals(name)) {
      return new UrlStatusFile(this);
    }
    return new UrlDownloadFile(this, name);
  }

  @Override
  public void save() {

    Path path = getPath();
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create directory " + path, e);
    }
    super.save();
  }

  public void createFile() {

    File f = new File(this.getPath() + File.separator + "urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create file " + getPath(), e);
    }

  }

  public void createFile(String os) {

    File f = new File(this.getPath() + File.separator + os + ".urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create file " + getPath(), e);
    }

  }

  public void createFile(String os, String arch) {

    File f = new File(this.getPath() + File.separator + os + "_" + arch + ".urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create file " + getPath(), e);
    }

  }

  // For development
  public void createJson() {

    File f = new File(this.getPath() + File.separator + "status.json");
    try {
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create file " + getPath(), e);
    }
  }
}
