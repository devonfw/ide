package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * JSON data object for an item of Intellij. We map only properties that we are interested in and let jackson ignore all
 * others.
 */
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
