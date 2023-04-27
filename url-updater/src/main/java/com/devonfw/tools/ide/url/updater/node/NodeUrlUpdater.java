package com.devonfw.tools.ide.url.updater.node;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for node.js.
 */
public class NodeUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "node";
  }

  @Override
  protected String getGithubOrganization() {

    return "nodejs";
  }

  @Override
  protected String getGithubRepository() {

    return "node";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://nodejs.org/dist/${version}/node-${version}-";
    doUpdateVersion(urlVersion, baseUrl + "win-x64.zip", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "win-aarch64.zip", WINDOWS, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "linux-x64.tar.gz", LINUX);
    doUpdateVersion(urlVersion, baseUrl + "linux-aarch64.tar.gz", LINUX, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "darwin-x64.tar.gz", MAC);
    doUpdateVersion(urlVersion, baseUrl + "darwin-aarch64.tar.gz", MAC, ARM64);
  }

}
