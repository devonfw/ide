package com.devonfw.tools.ide.migrator.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.logging.Output;
import com.devonfw.tools.ide.migrator.line.LineMigration;

/**
 * Implementation of {@link FileMigration} for textual files that are replaced line by line.
 */
public class TextFileMigration extends FileMigration {

  /** {@link Pattern} for Java files. */
  public static final Pattern JAVA_PATTERN = Pattern.compile(".*\\.java");

  /** {@link Pattern} for spring {@code application*.properties}. */
  public static final Pattern APPLICATION_PROPERTIES_PATTERN = Pattern.compile("application.*\\.properties");

  private final List<LineMigration> lineMigrations;

  /**
   * The constructor.
   *
   * @param filePattern the {@link Pattern} to match the file name.
   */
  public TextFileMigration(Pattern filePattern) {

    super(filePattern);
    this.lineMigrations = new ArrayList<>();
  }

  @Override
  protected void migrateFile(File file) throws IOException {

    for (LineMigration lineMigration : this.lineMigrations) {
      lineMigration.init(file);
    }
    StringBuilder sb = new StringBuilder(4096);
    boolean changed = false;
    try (FileInputStream in = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        BufferedReader reader = new BufferedReader(isr)) {

      while (true) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }
        String migratedLine = migrateLine(line);
        if (!changed && !migratedLine.equals(line)) {
          changed = true;
        }
        sb.append(migratedLine);
        sb.append('\n');
      }
    }
    for (LineMigration lineMigration : this.lineMigrations) {
      lineMigration.clear();
    }
    if (!changed) {
      return;
    }
    Output.get().info("Migrating file: %s", file.getPath());
    String content = sb.toString();
    try (FileOutputStream out = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8")) {
      writer.write(content);
    }
  }

  /**
   * @return lineMigrations
   */
  public List<LineMigration> getLineMigrations() {

    return this.lineMigrations;
  }

  private String migrateLine(String line) {

    for (LineMigration lineMigration : this.lineMigrations) {
      line = lineMigration.migrateLine(line);
    }
    return line;
  }

}
