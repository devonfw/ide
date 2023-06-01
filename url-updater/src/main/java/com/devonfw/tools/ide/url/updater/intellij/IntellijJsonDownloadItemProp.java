package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonProperty;


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
