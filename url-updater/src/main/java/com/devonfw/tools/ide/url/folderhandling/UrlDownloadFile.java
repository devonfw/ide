package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;

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


  public Set<String> getUrls() {
    return this.urls;
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


  public Set<Double> generateUrlHashes() {
    Set<Double> urlHashes = new HashSet<>();
    for (String url: this.urls) {
      double urlHash = url.hashCode();
      urlHashes.add(urlHash);
    }
    return urlHashes;

  }

  @Override
  public void doLoad() {

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
  public void doSave() {

    Path path = getPath();
    try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
      for (String line : this.urls) {
        bw.write(line + "\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save file " + path, e);
    }
  }
}
