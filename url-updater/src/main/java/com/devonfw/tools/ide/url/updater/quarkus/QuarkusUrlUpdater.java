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
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}";
    doUpdateVersion(urlVersion, baseUrl + ".zip", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + ".tar.gz");
  }

}