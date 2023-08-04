package com.devonfw.tools.ide.migrator.file;

import java.io.File;
import java.io.FileFilter;

/**
 * {@link FileFilter} {@link #accept(File) accepting} all {@link File}s.
 */
public class FileFilterNone implements FileFilter {

  /** Singleton instance. */
  public static final FileFilterNone INSTANCE = new FileFilterNone();

  @Override
  public boolean accept(File pathname) {

    return false;
  }

}
