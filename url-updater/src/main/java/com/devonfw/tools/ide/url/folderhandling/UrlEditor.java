package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Class to enable easier usage of download-url-folders-structure by handling technicalities in the background. Supposed
 * to be used to interact with crawler class to get or remove download-urls. Gives a method to create a folder or file,
 * while objects are created in the background, as well as a method for getting an object representing a folder or a
 * file. Also a method for adding and removing urls is given.
 *
 * @deprecated use {@link UrlRepository} and its children directly.
 */
@Deprecated
public class UrlEditor {

  protected Path repoPath;

  UrlRepository urlRepoObject;

  public UrlEditor(String PathToUrlRepo) {

    this.repoPath = Paths.get(PathToUrlRepo);

    this.urlRepoObject = new UrlRepository(this.repoPath);
    UrlReader<AbstractUrlArtifact> urlReaderObject = new UrlReader<AbstractUrlArtifact>();
    urlReaderObject.readFolderStructure(this.urlRepoObject);
  }

  // TO DO: Womöglich sollte der zweite Konstruktor entfernt werden,
  // da es nicht nötig ist das UrlRepository Objekt gesondert einzugeben.
  public UrlEditor(UrlRepository urlRepoObject) {

    // this.repoPath = Paths.get(PathToUrlRepo);
    this.repoPath = urlRepoObject.getPath();
    UrlReader<AbstractUrlArtifact> urlReaderObject = new UrlReader<AbstractUrlArtifact>();
    urlReaderObject.readFolderStructure(urlRepoObject);

  }

  public UrlRepository getUrlRepositoryObject() {

    return this.urlRepoObject;
  }

  public void createFolder(String Tool) {

    this.urlRepoObject.getOrCreateChild(Tool);
    File f = new File(this.repoPath + File.separator + Tool);
    f.mkdirs();
  }

  public void createFolder(String Tool, String Edition) {

    this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition);
    File f = new File(this.repoPath + File.separator + Tool + File.separator + Edition);
    f.mkdirs();
  }

  public void createFolder(String Tool, String Edition, String Version) {

    this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version);
    File f = new File(this.repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version);
    f.mkdirs();
  }

  public void createFile(String Tool, String Edition, String Version) {

    this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateChild("urls");
    File f = new File(this.repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version
        + File.separator + "urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createFile(String Tool, String Edition, String Version, String os) {

    this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateChild(os + ".urls");
    File f = new File(this.repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version
        + File.separator + os + ".urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createFile(String Tool, String Edition, String Version, String os, String arch) {

    this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateChild(os + "_" + arch + ".urls");
    File f = new File(this.repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version
        + File.separator + os + "_" + arch + ".urls");
    try {
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the Folders object representation of type UrlTool.
   *
   * @param Tool
   * @return
   */
  public UrlTool getFolder(String Tool) {

    return this.urlRepoObject.getChild(Tool);

  }

  /**
   * Get the Folders object representation of type UrlEdition.
   *
   * @param Tool
   * @param Edition
   * @return
   */
  public UrlEdition getFolder(String Tool, String Edition) {

    return this.urlRepoObject.getChild(Tool).getChild(Edition);
  }

  /**
   * Get the Folders object representation of type UrlVersion.
   *
   * @param Tool
   * @param Edition
   * @param Version
   * @return
   */
  public UrlVersion getFolder(String Tool, String Edition, String Version) {

    return this.urlRepoObject.getChild(Tool).getChild(Edition).getChild(Version);
  }

  /**
   * Returns an UrlFile object that represents the file with urls called "urls". The method without os or arch
   * parameters should only be used if there are no distinctions to be made regarding an users operating system or
   * system architecture.
   *
   * @param Tool
   * @param Edition
   * @param Version
   * @return
   */
  public UrlDownloadFile getFile(String Tool, String Edition, String Version) {

    return this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateUrls();
  }

  /**
   * Returns an UrlFile object that represents the file named ${os}.urls.
   *
   * @param Tool
   * @param Edition
   * @param Version
   * @param os
   * @return
   */
  public UrlDownloadFile getFile(String Tool, String Edition, String Version, String os) {

    return this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateUrls(os);
  }

  /**
   * Returns an UrlFile object that represents the file named ${os}_%{arch}.urls.
   *
   * @param Tool
   * @param Edition
   * @param Version
   * @param os
   * @param arch
   * @return
   */
  public UrlDownloadFile getFile(String Tool, String Edition, String Version, String os, String arch) {

    return this.urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version)
        .getOrCreateUrls(os, arch);
  }

  /**
   * Load file content into set of object, add possibly new urls to set and overwrite file content with the content of
   * the objects set.
   *
   * @param urlsList
   * @param urlFile
   */
  public void addUrls(Set<String> urlsList, UrlDownloadFile urlFile) {

    try {
      urlFile.loadLinesOfFileIntoObject();
      urlFile.addToObjectsList(urlsList);
      urlFile.saveListFromObjectIntoFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Load file content into set of object, add possibly new urls to set and overwrite file content with the content of
   * the objects set.
   *
   * @param url
   * @param urlFile
   */
  public void addUrls(String url, UrlDownloadFile urlFile) {

    try {
      urlFile.loadLinesOfFileIntoObject();
      urlFile.addToObjectsList(url);
      urlFile.saveListFromObjectIntoFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Load file content into set of object, remove urls from set and overwrite file content with the content of the
   * objects set.
   *
   * @param urlsList
   * @param urlFile
   */
  public void removeUrls(Set<String> urlsList, UrlDownloadFile urlFile) {

    try {
      urlFile.loadLinesOfFileIntoObject();
      urlFile.removeLineFromObjectsList(urlsList);
      urlFile.saveListFromObjectIntoFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Load file content into set of object, remove urls from set and overwrite file content with the content of the
   * objects set.
   *
   * @param url
   * @param urlFile
   */
  public void removeUrls(String url, UrlDownloadFile urlFile) {

    try {
      urlFile.loadLinesOfFileIntoObject();
      urlFile.removeLineFromObjectsList(url);
      urlFile.saveListFromObjectIntoFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
