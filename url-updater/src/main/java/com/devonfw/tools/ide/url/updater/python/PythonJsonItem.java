package com.devonfw.tools.ide.url.updater.python;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Json file Object for Python content, Mapping just the needed Properties
 */
public class PythonJsonItem {

  @JsonProperty("version")
  private String version;

  @JsonProperty("files")
  private List<PythonFiles> files;

  public String getVersion() {

    return this.version;
  }

  public List<PythonFiles> getFiles() {

    return files;
  }
}
