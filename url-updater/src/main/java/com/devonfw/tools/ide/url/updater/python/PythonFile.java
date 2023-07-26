package com.devonfw.tools.ide.url.updater.python;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Files array in the python json file. Mapping just the needed properties
 */
public class PythonFile {

  @JsonProperty("filename")
  private String filename;

  @JsonProperty("arch")
  private String arch;

  @JsonProperty("platform")
  private String platform;

  @JsonProperty("download_url")
  private String download_url;

  public String getFilename() {

    return filename;
  }

  public String getPlatform() {

    return platform;
  }

  public String getArch() {

    return arch;
  }

  public String getDownloadUrl() {

    return download_url;
  }
}
