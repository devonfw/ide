package com.devonfw.tools.ide.url.updater.androidstudio;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON data object for an item of Android. We map only properties that we are interested in and let jackson ignore all
 * others.
 */
public class AndroidJsonItem {

  @JsonProperty("version")
  private String version;

  /**
   * @return version
   */
  public String getVersion() {

    return this.version;
  }

}