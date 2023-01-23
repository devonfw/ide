package com.devonfw.tools.ide.url.updater.java;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JavaJsonVersion {
    private String openjdk_version;
    private String semver;

    public JavaJsonVersion(){}

    public JavaJsonVersion(String openjdk_version, String semver) {
        this.openjdk_version = openjdk_version;
        this.semver = semver;
    }

    @JsonProperty("openjdk_version")
    public String getOpenjdk_version() {
        return openjdk_version;
    }

    @JsonProperty("openjdk_version")
    public void setOpenjdk_version(String openjdk_version) {
        this.openjdk_version = openjdk_version;
    }

    @JsonProperty("semver")
    public String getSemver() {
        return semver;
    }

    @JsonProperty("semver")
    public void setSemver(String semver) {
        this.semver = semver;
    }
}