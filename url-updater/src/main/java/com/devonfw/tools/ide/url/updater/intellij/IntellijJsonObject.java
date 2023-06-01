package com.devonfw.tools.ide.url.updater.intellij;

import com.devonfw.tools.ide.common.JsonObject;

import java.util.List;

public class IntellijJsonObject implements JsonObject {

  private List<IntellijJsonReleases> releases;

  public List<IntellijJsonReleases> getReleases() {

    return releases;
  }

}
