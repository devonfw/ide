package com.devonfw.tools.ide.url.updater.mavenapiclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
  private String groupId;

  private String artifactId;

  private Versioning versioning;

  public Metadata() {

  }

  public Metadata(String groupId, String artifactId, Versioning versioning) {

    this.groupId = groupId;
    this.artifactId = artifactId;
    this.versioning = versioning;
  }

  @JsonProperty("groupId")
  public String getGroupId() {

    return groupId;
  }

  @JsonProperty("groupId")
  public void setGroupId(String groupId) {

    this.groupId = groupId;
  }

  @JsonProperty("artifactId")
  public String getArtifactId() {

    return artifactId;
  }

  @JsonProperty("artifactId")
  public void setArtifactId(String artifactId) {

    this.artifactId = artifactId;
  }

  @JsonProperty("versioning")
  public Versioning getVersioning() {

    return versioning;
  }

  @JsonProperty("versioning")
  public void setVersioning(Versioning versioning) {

    this.versioning = versioning;
  }
}
