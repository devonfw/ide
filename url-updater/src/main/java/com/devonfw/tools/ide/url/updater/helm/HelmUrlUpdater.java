package com.devonfw.tools.ide.url.updater.helm;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for "helm".
 */
public class HelmUrlUpdater extends GithubUrlUpdater {

  @Override
  protected String getTool() {

    return "helm";
  }

  @Override
  protected String getVersionPrefixToRemove() {

    return "v";
  }

  @Override
  protected String getGithubOrganization() {

    return "helm";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://get.helm.sh/helm-v${version}-";
    doAddVersion(urlVersion, baseUrl + "windows-amd64.zip", WINDOWS);
    doAddVersion(urlVersion, baseUrl + "linux-amd64.tar.gz", LINUX);
    doAddVersion(urlVersion, baseUrl + "darwin-amd64.tar.gz", MAC);
    if (doVersionMatchWithMinor(urlVersion.getName(), 3, 5))
      doAddVersion(urlVersion, baseUrl + "darwin-arm64.tar.gz", MAC, ARM64);
  }

}
