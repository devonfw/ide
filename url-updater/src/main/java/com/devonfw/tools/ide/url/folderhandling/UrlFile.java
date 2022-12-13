package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * An instance of this class represents a file given by the files folder path and the files name. The instance can read
 * the files content, edit the content taken out and write it back into the file by methods defined in this class.
 *
 */
public class UrlFile extends UrlHasParentArtifact<UrlVersion> {

  private final Set<String> linesOfFile = new HashSet<>();

  public UrlFile(UrlVersion parent, String name) {

    super(parent, name);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public Set<String> loadLinesOfFileIntoObject() throws IOException {

    String line;
    BufferedReader bufferedReaderObj = new BufferedReader(new FileReader(getPath().toString()));
    while ((line = bufferedReaderObj.readLine()) != null) {
      this.linesOfFile.add(line);
    }
    bufferedReaderObj.close();
    return this.linesOfFile;

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

    return this.linesOfFile;
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void addToObjectsList(String urlToAdd) throws IOException {

    if (!this.linesOfFile.contains(urlToAdd)) {
      this.linesOfFile.add(urlToAdd);
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void addToObjectsList(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      if (!this.linesOfFile.contains(url)) {
        this.linesOfFile.add(url);
      }
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void removeLineFromObjectsList(String urlToRemove) throws IOException {

    this.linesOfFile.remove(urlToRemove);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void removeLineFromObjectsList(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      this.linesOfFile.remove(url);
    }
  }

  /**
   * @deprecated
   */
  @Deprecated
  public void saveListFromObjectIntoFile() throws IOException {

    Files.delete(getPath());
    BufferedWriter bw = Files.newBufferedWriter(getPath(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    for (String line : this.linesOfFile) {
      bw.write(line + "\n");
    }
    bw.close();
  }

}
