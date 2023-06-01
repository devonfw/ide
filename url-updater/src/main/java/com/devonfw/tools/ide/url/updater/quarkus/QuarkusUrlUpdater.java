package com.devonfw.tools.ide.url.updater.quarkus;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for quarkus CLI.
 */
public class QuarkusUrlUpdater extends GithubUrlUpdater {

  @Override
  protected String getTool() {

    return "quarkus";
  }

  @Override
  protected String getGithubOrganization() {

    return "quarkusio";
  }

  @Override
  protected String getGithubRepository() {

    return "quarkus";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String version = urlVersion.getName();

    if (isVersionGreaterThan(version, "2.5.0")) {
      String baseUrl = "https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}";
      doAddVersion(urlVersion, baseUrl + ".zip", WINDOWS);
      doAddVersion(urlVersion, baseUrl + ".tar.gz");
    }
  }

}