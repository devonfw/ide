package com.devonfw.tools.ide.url.model.folder;

import com.devonfw.tools.ide.url.model.AbstractUrlFolderWithParent;
import com.devonfw.tools.ide.url.model.file.UrlSecurityFile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * An {@link UrlFolder} representing the actual edition of a {@link UrlTool}. The default edition may have the same
 * {@link #getName() name} as the {@link UrlTool} itself. However, tools like "intellij" may have editions like
 * "community" or "ultimate".
 */
public class UrlEdition extends AbstractUrlFolderWithParent<UrlTool, UrlVersion> {

  private UrlSecurityFile securityFile;

  /** List of OS types which need to be checked for existence */
  private static final List<String> osTypes = Arrays.asList("linux_x64.urls", "mac_arm64.urls", "mac_x64.urls",
      "windows_x64.urls");

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public UrlEdition(UrlTool parent, String name) {

    super(parent, name);
  }

  /**
   * This method is used to add new children to the children collection of an instance from this class.
   *
   * @param name The name of the {@link UrlVersion} object that should be created.
   */
  @Override
  protected UrlVersion newChild(String name) {

    return new UrlVersion(this, name);
  }

  /**
   * Retrieves OS types, can be used to overwrite current OS types
   *
   * @param osTypesOverwrite List of OS types to overwrite
   * @return List of OS types
   */
  protected List<String> retrieveOsTypes(List<String> osTypesOverwrite) {

    if (osTypesOverwrite != null && !osTypesOverwrite.isEmpty()) {
      return osTypesOverwrite;
    } else {
      return osTypes;
    }
  }

  /**
   * @return the {@link UrlSecurityFile} of this {@link UrlEdition}. Will be lazily initialized on the first call of
   *         this method. If the file exists, it will be loaded, otherwise it will be empty and only created on save if
   *         data was added.
   */
  public UrlSecurityFile getSecurityFile() {

    if (this.securityFile == null) {
      this.securityFile = new UrlSecurityFile(this);
      load(this.securityFile);
    }
    return this.securityFile;
  }

  @Override
  public void save() {

    super.save();
    if (this.securityFile != null) {
      this.securityFile.save();
    }
  }

  /**
   * Checks if an OS type was missing
   *
   * @returns true if an OS type was missing, false if not
   */
  public boolean isMissingOs(String version, List<String> osTypesOverwrite) {

    Collection<UrlVersion> childNames = this.getChildren();
    int osCount = 0;
    List<String> osTypes = retrieveOsTypes(osTypesOverwrite);
    for (UrlVersion name : childNames) {
      if (name.getName().equals(version)) {
        Set<String> osNames = name.getChildNames();

        for (String osName : osNames) {
          if (osTypes.contains(osName)) {
            osCount++;
          }
        }
      }
    }

    return osCount != osTypes.size();
  }

}
