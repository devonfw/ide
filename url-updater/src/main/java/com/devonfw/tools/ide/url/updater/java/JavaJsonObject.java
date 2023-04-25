package com.devonfw.tools.ide.url.updater.java;

import java.util.ArrayList;
import java.util.List;

import com.devonfw.tools.ide.url.updater.JsonObject;

public class JavaJsonObject implements JsonObject {
  private final List<JavaJsonVersion> versions = new ArrayList<>();

  public List<JavaJsonVersion> getVersions() {

    return versions;
  }
}
