package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link UrlFile} with the download URLs. Its {@link #getName() name} has to follow one of the following conventions:
 * <ul>
 * <li>«os»_«arch».urls</li>
 * <li>«os».urls</li>
 * <li>urls</li>
 * </ul>
 */
public class UrlDownloadFile extends AbstractUrlFile {

  private final Set<String> urls = new HashSet<>();

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public UrlDownloadFile(UrlVersion parent, String name) {

    super(parent, name);
  }

  /**
   * @return the number of #getUrl
   */
  public int getUrlCount() {

    return this.urls.size();
  }

  /**
   * @param url the download URL to add.
   */
  public void addUrl(String url) {

    boolean added = this.urls.add(url);
    if (added) {
      this.modified = true;
    }
  }

  /**
   * @param url the download URL to remove.
   */
  public void removeUrl(String url) {

    boolean removed = this.urls.remove(url);
    if (removed) {
      this.modified = true;
    }
  }

  @Override
  protected void doLoad() {

    this.urls.clear();
    Path path = getPath();
    try (BufferedReader br = Files.newBufferedReader(path)) {
      String line;
      do {
        line = br.readLine();
        if (line != null) {
          this.urls.add(line.trim());
        }
      } while (line != null);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load file " + path, e);
    }
  }

  @Override
  protected void doSave() {

    Path path = getPath();
    try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.WRITE)) {
      for (String line : this.urls) {
        bw.write(line + "\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save file " + path, e);
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public Set<String> loadLinesOfFileIntoObject() throws IOException {

    if (this.modified) {
      load();
    }
    return Collections.unmodifiableSet(this.urls);
  }

  /**
   * Only intended for debugging and testing purposes to get content of file easily for comparison.
   *
   * @return
   * @throws IOException
   * @deprecated
   */
  @Deprecated
  public Set<String> debugLoadFileContent() throws IOException {

    String line;
    BufferedReader bufferedReaderObj = new BufferedReader(new FileReader(getPath().toString()));
    Set<String> debugLinesOfFile = new HashSet<>();
    while ((line = bufferedReaderObj.readLine()) != null) {
      debugLinesOfFile.add(line);
    }
    bufferedReaderObj.close();
    return debugLinesOfFile;
  }

  /**
   * @deprecated
   */
  @Deprecated
  public Set<String> getObjectsSet() {

    return Collections.unmodifiableSet(this.urls);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void addToObjectsList(String urlToAdd) throws IOException {

    addUrl(urlToAdd);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void addToObjectsList(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      addUrl(url);
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void removeLineFromObjectsList(String urlToRemove) throws IOException {

    removeUrl(urlToRemove);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void removeLineFromObjectsList(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      removeUrl(url);
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void saveListFromObjectIntoFile() throws IOException {

    save();
  }

}
