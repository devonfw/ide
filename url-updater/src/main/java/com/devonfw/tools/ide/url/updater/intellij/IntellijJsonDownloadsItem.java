package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;

/**
 * JSON data object for an item of Intellij. We map only properties that we are interested in and let jackson ignore all
 * others.
 */
public class IntellijJsonDownloadsItem {

  @JsonAnySetter
  private Map<String, Object> os_values;

  public Map<String, Object> getOs_values() {

    return os_values;
  }
}
