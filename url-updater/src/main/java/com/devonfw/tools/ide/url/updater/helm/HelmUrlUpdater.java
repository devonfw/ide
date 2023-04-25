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
  protected String getGithubOrganization() {

    return "helm";
  }

  @Override
  protected String mapVersion(String version) {

    if (version.startsWith("v")) {
      version = version.substring(1);
    }
    return version;
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://get.helm.sh/helm-v${version}-";
    doUpdateVersion(urlVersion, baseUrl + "windows-amd64.zip", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "linux-amd64.tar.gz", LINUX);
    doUpdateVersion(urlVersion, baseUrl + "darwin-amd64.tar.gz", MAC);
    doUpdateVersion(urlVersion, baseUrl + "darwin-arm64.tar.gz", MAC, ARM64);
  }

}
