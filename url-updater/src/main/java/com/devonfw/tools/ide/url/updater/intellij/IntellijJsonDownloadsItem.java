package com.devonfw.tools.ide.url.updater.intellij;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;

public class IntellijJsonDownloadsItem {

  @JsonAnySetter
  private Map<String, Object> os_values;

  public Map<String, Object> getOs_values() {

    return os_values;
  }
}
