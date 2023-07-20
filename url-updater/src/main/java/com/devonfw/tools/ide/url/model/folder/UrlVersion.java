package com.devonfw.tools.ide.url.model.folder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.common.SystemArchitecture;
import com.devonfw.tools.ide.url.model.AbstractUrlFolderWithParent;
import com.devonfw.tools.ide.url.model.file.UrlChecksum;
import com.devonfw.tools.ide.url.model.file.UrlDownloadFile;
import com.devonfw.tools.ide.url.model.file.UrlFile;
import com.devonfw.tools.ide.url.model.file.UrlStatusFile;
import com.devonfw.tools.ide.version.VersionIdentifier;

/**
 * An {@link UrlFolder} representing the actual version of an {@link UrlEdition}. Examples for the {@link #getName()
 * name} of such version could be "1.6.2" or "17.0.5_8".
 */
public class UrlVersion extends AbstractUrlFolderWithParent<UrlEdition, UrlFile<?>> {

  private VersionIdentifier versionIdentifier;

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

    return getOrCreateUrls(null, null);
  }

  /**
   * @param os the optional {@link OperatingSystem}.
   * @return the {@link UrlDownloadFile} {@link #getName() named} "«os».urls". Will be created if it does not exist.
   */
  public UrlDownloadFile getOrCreateUrls(OperatingSystem os) {

    return getOrCreateUrls(os, null);
  }

  /**
   * @param os the optional {@link OperatingSystem}.
   * @param arch the architecture (e.g. "x64" or "arm64").
   * @return the {@link UrlDownloadFile} {@link #getName() named} "«os»_«arch».urls". Will be created if it does not
   *     exist.
   */
  public UrlDownloadFile getOrCreateUrls(OperatingSystem os, SystemArchitecture arch) {

    return (UrlDownloadFile) getOrCreateChild(getUrlsFileName(os, arch));
  }

  /**
   * @return the {@link UrlDownloadFile} {@link #getName() named} "urls".
   */
  public UrlDownloadFile getUrls() {

    return getUrls(null, null);
  }

  /**
   * @param os the optional {@link OperatingSystem}.
   * @param arch the architecture (e.g. "x64" or "arm64").
   * @return the {@link UrlDownloadFile} {@link #getName() named} "«os»_«arch».urls".
   */
  public UrlDownloadFile getUrls(OperatingSystem os, SystemArchitecture arch) {

    return (UrlDownloadFile) getChild(getUrlsFileName(os, arch));
  }

  /**
   * @param os the optional {@link OperatingSystem}.
   * @param arch the architecture (e.g. "x64" or "arm64").
   * @return String of the format "«os»_«arch».urls".
   */
  public static String getUrlsFileName(OperatingSystem os, SystemArchitecture arch) {

    if ((os == null) && (arch == null)) {
      return "urls";
    }
    return os + "_" + SystemArchitecture.orDefault(arch) + ".urls";
  }

  /**
   * @return the {@link UrlStatusFile}.
   */
  public UrlStatusFile getOrCreateStatus() {

    return (UrlStatusFile) getOrCreateChild(UrlStatusFile.STATUS_JSON);
  }

  /**
   * @return the {@link VersionIdentifier}
   */
  public VersionIdentifier getVersionIdentifier() {

    if (this.versionIdentifier == null) {
      this.versionIdentifier = VersionIdentifier.of(getName());
    }
    return this.versionIdentifier;
  }

  /**
   * @param urlsFilename the {@link #getName() filename} of the URLs file.
   * @return the existing or newly created and added {@link UrlChecksum} file.
   */
  public UrlChecksum getOrCreateChecksum(String urlsFilename) {

    return (UrlChecksum) getOrCreateChild(urlsFilename + UrlChecksum.EXTENSION);
  }

  /**
   * @param urlsFilename the {@link #getName() filename} of the URLs file.
   * @return the existing {@link UrlChecksum} file.
   */
  public UrlChecksum getChecksum(String urlsFilename) {

    return (UrlChecksum) getChild(urlsFilename + UrlChecksum.EXTENSION);
  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the {@link UrlFile} object that should be created.
   */
  @Override
  protected UrlFile<?> newChild(String name) {

    if (Objects.equals(name, UrlStatusFile.STATUS_JSON)) {
      return new UrlStatusFile(this);
    } else if (name.endsWith(UrlChecksum.EXTENSION)) {
      return new UrlChecksum(this, name);
    }
    return new UrlDownloadFile(this, name);
  }

  @Override
  protected boolean isAllowedChild(String name, boolean folder) {

    return true;
  }

  @Override
  public void save() {

    if (getChildCount() == 0) {
      return;
    }
    Path path = getPath();
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create directory " + path, e);
    }
    super.save();
  }

}
