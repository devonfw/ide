package com.devonfw.ide.configurator;

import java.io.File;

import com.devonfw.ide.configurator.logging.Log;

/**
 * TODO hohwille This type ...
 *
 * @since 3.0.0
 */
public class Args {

  private final String[] args;

  private int i;

  /**
   * The constructor.
   */
  public Args(String... args) {

    this.args = args;
    this.i = 0;
  }

  public boolean hasNext() {

    return (this.i < this.args.length);
  }

  public String current() {

    if (this.i < this.args.length) {
      return this.args[this.i];
    }
    return null;
  }

  public String next() {

    if (this.i < this.args.length) {
      return this.args[this.i++];
    }
    return null;
  }

  public File nextFile(File file) {

    String arg = current();
    if (file != null) {
      Log.warn("Duplicate option '" + arg + "'.");
    }
    File result = file;
    if (this.i < this.args.length) {
      result = new File(this.args[this.i++]);
    } else {
      Log.err("Option '" + arg + "' has to be followed by a file argument.");
    }
    return result;
  }

}
