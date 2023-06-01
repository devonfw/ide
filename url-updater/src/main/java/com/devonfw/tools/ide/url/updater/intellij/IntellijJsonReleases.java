package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class IntellijJsonReleases {
  @JsonProperty("version")
  private String version;

  @JsonProperty("downloads")
  private Map<String, IntellijJsonDownloadsItem> downloads;

  public String getVersion() {

    return version;
  }

  public Map<String, IntellijJsonDownloadsItem> getDownloads() {

    return downloads;
  }

}
