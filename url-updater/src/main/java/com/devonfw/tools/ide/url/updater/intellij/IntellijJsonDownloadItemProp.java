package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON data object for an item of Intellij. We map only properties that we are interested in and let jackson ignore all
 * others.
 */
public class IntellijJsonDownloadItemProp {
  @JsonProperty("link")
  private String link;

  @JsonProperty("checksumLink")
  private String checksumLink;

  public String getLink() {

    return link;
  }

  public String getChecksumLink() {

    return checksumLink;
  }
}
