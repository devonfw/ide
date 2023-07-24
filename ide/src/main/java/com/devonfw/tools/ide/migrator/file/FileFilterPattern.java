package com.devonfw.tools.ide.migrator.file;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * {@link FileFilter} {@link #accept(File) accepting} only {@link File}s that (do not) match a given regxe.
 */
public class FileFilterPattern implements FileFilter {

  private final Pattern pattern;

  private final boolean result;

  /**
   * The constructor.
   *
   * @param pattern the {@link Pattern} to match.
   * @param result the result of {@link #accept(File)} if the given {@link Pattern} matches.
   */
  public FileFilterPattern(Pattern pattern, boolean result) {

    super();
    this.pattern = pattern;
    this.result = result;
  }

  @Override
  public boolean accept(File file) {

    String name = file.getName();
    if (this.pattern.matcher(name).matches()) {
      return this.result;
    }
    return !this.result;
  }

  /**
   * @param regex the regex {@link Pattern} to match.
   * @return the {@link FileFilterPattern} that {@link #accept(File) accepts} only files that have a
   *         {@link File#getName() name} matching the given {@link Pattern}.
   */
  public static FileFilterPattern accept(String regex) {

    return new FileFilterPattern(Pattern.compile(regex), true);
  }

  /**
   * @param regex the regex {@link Pattern} to match.
   * @return the {@link FileFilterPattern} that {@link #accept(File) accepts} only files that have a
   *         {@link File#getName() name} <b>not</b> matching the given {@link Pattern}.
   */
  public static FileFilterPattern reject(String regex) {

    return new FileFilterPattern(Pattern.compile(regex), false);
  }

}
