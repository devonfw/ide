package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An instance of this class represents a file given by the files folder path and the files name. The instance can read
 * the files content, edit the content taken out and write it back into the file by the methods defined in this class.
 * The idea of this class is to modify the files content in the last step, to avoid fallacious content in the file.
 * To do so, at first the files content is read into a set of the object.
 * Then the set is manipulated by adding and removing URL's. Finally, if nothing went wrong,
 * the sets content is written into the file as new content.
 * @see {@link UrlEditor} for the methods addUrls and removeUrls. They are based on methods from the class {@link UrlFile} and implement
 * the above mentioned idea, by combining this classes methods.
 */
public class UrlFile extends UrlHasParentArtifact<UrlVersion> {

  Set<String> linesOfFile = new HashSet<>();

  public UrlFile(UrlVersion parent, String name) {

    super(parent, name);
    try {
      loadLinesOfFileIntoObject();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public Set<String> loadLinesOfFileIntoObject() throws IOException {

    String line;
    BufferedReader bufferedReaderObj = new BufferedReader(new FileReader(path.toString()));
    while ((line = bufferedReaderObj.readLine()) != null) {
      linesOfFile.add(line);
    }
    bufferedReaderObj.close();
    return linesOfFile;

  }

  /**
   * Only intended for debugging and testing purposes to get content of file easily for comparison.
   *
   * @return
   * @throws IOException
   */
  public Set<String> debugLoadFileContent() throws IOException {

    String line;
    BufferedReader bufferedReaderObj = new BufferedReader(new FileReader(path.toString()));
    Set<String> debugLinesOfFile = new HashSet<>();
    while ((line = bufferedReaderObj.readLine()) != null) {
      debugLinesOfFile.add(line);
    }
    bufferedReaderObj.close();
    return debugLinesOfFile;
  }

  public Set<String> getObjectsSet() {

    return this.linesOfFile;
  }

  public void addToObjectsSet(String urlToAdd) throws IOException {

    if (!linesOfFile.contains(urlToAdd)) {
      linesOfFile.add(urlToAdd);
    }
  }

  public void addToObjectsSet(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      if (!linesOfFile.contains(url)) {
        linesOfFile.add(url);
      }
    }
  }

  public void removeLineFromObjectsSet(String urlToRemove) throws IOException {

    linesOfFile.remove(urlToRemove);
  }

  public void removeLineFromObjectsSet(Set<String> urlsList) throws IOException {

    for (String url : urlsList) {
      linesOfFile.remove(url);
    }
  }

  public void saveSetFromObjectIntoFile() throws IOException {

    Files.delete(path);
    BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    for (String line : linesOfFile) {
      bw.write(line + "\n");
    }
    bw.close();
  }

}
