package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * JSON data object for an item of Intellij. We map only properties that we are interested in and let jackson ignore all
 * others.
 */
public class IntellijJsonDownloads {

  @JsonProperty("downloads")
  private List<IntellijJsonDownloadsItem> downloads;

  public List<IntellijJsonDownloadsItem> getDownloads() {

    return downloads;
  }

}
