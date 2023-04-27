package com.devonfw.tools.ide.url.updater.gh;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for "gh" (github CLI).
 */
public class GhUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "gh";
  }

  @Override
  protected String mapVersion(String version) {

    String version2 = version.replaceAll("v", "");
    // filter out pre releases
    if (version2.contains("-")) {
      return null;
    }
    return version2;
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_";
    doUpdateVersion(urlVersion, baseUrl + "windows_amd64.zip", WINDOWS, X64);
    doUpdateVersion(urlVersion, baseUrl + "linux_amd64.tar.gz", LINUX, X64);
    doUpdateVersion(urlVersion, baseUrl + "linux_arm64.tar.gz", LINUX, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "macOS_amd64.tar.gz", MAC, X64);
    doUpdateVersion(urlVersion, baseUrl + "macOS_arm64.tar.gz", MAC, ARM64);
  }

  @Override
  protected String getGithubOrganization() {

    return "cli";
  }

  @Override
  protected String getGithubRepository() {

    return "cli";
  }
}
