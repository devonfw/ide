package com.devonfw.tools.ide.url.updater.java;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JavaJsonVersion {
  @JsonProperty("openjdk_version")
  private String openjdk_version;

  @JsonProperty("semver")
  private String semver;

  public JavaJsonVersion() {

  }

  public JavaJsonVersion(String openjdk_version, String semver) {

    this.openjdk_version = openjdk_version;
    this.semver = semver;
  }

  public String getOpenjdk_version() {

    return openjdk_version;
  }

  public void setOpenjdk_version(String openjdk_version) {

    this.openjdk_version = openjdk_version;
  }

  public String getSemver() {

    return semver;
  }

  public void setSemver(String semver) {

    this.semver = semver;
  }
}