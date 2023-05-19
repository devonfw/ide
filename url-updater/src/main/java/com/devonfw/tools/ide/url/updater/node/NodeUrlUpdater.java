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
  protected String getVersionPrefixToRemove() {

    return "v";
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
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://nodejs.org/dist/v${version}/node-v${version}-";
    if (doVersionMatchWithMinor(urlVersion.getName(), 4, 0)) {
      doAddVersion(urlVersion, baseUrl + "win-x64.zip", WINDOWS);
      if (doVersionMatchWithMinor(urlVersion.getName(), 20, 0))
        doAddVersion(urlVersion, baseUrl + "win-arm64.zip", WINDOWS, ARM64);
      doAddVersion(urlVersion, baseUrl + "linux-x64.tar.gz", LINUX);
      doAddVersion(urlVersion, baseUrl + "linux-arm64.tar.gz", LINUX, ARM64);
      doAddVersion(urlVersion, baseUrl + "darwin-x64.tar.gz", MAC);
      if (doVersionMatchWithMinor(urlVersion.getName(), 16, 0))
        doAddVersion(urlVersion, baseUrl + "darwin-arm64.tar.gz", MAC, ARM64);
    }
  }

}
