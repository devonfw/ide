package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;

/**
 * {@link AbstractUrlFile} for the checksum of a binary download file.
 */
public class UrlChecksum extends AbstractUrlFile {

  /** The file extension of the checksum file (including the dot). */
  public static final String EXTENSION = ".sha256";

  /** The hash algorithm to use ({@value}). */
  public static final String HASH_ALGORITHM = "SHA-256";

  private String checksum;

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   * @param name the {@link #getName() filename}.
   */
  public UrlChecksum(UrlVersion parent, String name) {

    super(parent, name);
  }

  /**
   * @return the checksum as {@link String} (hex-representation).
   */
  public String getChecksum() {

    return checksum;
  }

  /**
   * @param checksum the new value of {@link #getChecksum()}.
   */
  public void setChecksum(String checksum) {

    this.checksum = checksum;
  }

  @Override
  protected void doLoad() {

    Path path = getPath();
    try {
      String cs = Files.readString(path);
      setChecksum(cs);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load file " + path, e);
    }
  }

  @Override
  protected void doSave() {

    Path path = getPath();
    try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
      bw.write(getChecksum() + "\n");

    } catch (IOException e) {
      throw new IllegalStateException("Failed to save file " + path, e);
    }
  }
}
