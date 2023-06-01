package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IntellijJsonDownloads {

  @JsonProperty("downloads")
  private List<IntellijJsonDownloadsItem> downloads;

  public List<IntellijJsonDownloadsItem> getDownloads() {

    return downloads;
  }

}
