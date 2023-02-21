package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFolderWithParent;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    if(os == null && arch == null){
      return getOrCreateUrls();
    }
    return (UrlDownloadFile) getOrCreateChild(os + "_" + getArchOrDefault(arch) + ".urls");
  }
  
  public UrlDownloadFile getUrlFile(String name){
    return (UrlDownloadFile) getChild(name);
  }


  private String getArchOrDefault(String arch) {
    if (arch == null) {
      return "x64";
    }
    return arch;
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

    if (Objects.equals(name, UrlStatusFile.STATUS_JSON)) {
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

}
