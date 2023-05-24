package com.devonfw.tools.ide.url.updater.gh;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;
import com.devonfw.tools.ide.version.VersionIdentifier;

/**
 * {@link GithubUrlUpdater} for "gh" (github CLI).
 */
public class GhUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "gh";
  }

  @Override
  protected String getVersionPrefixToRemove() {

    return "v";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    VersionIdentifier vid = VersionIdentifier.of(urlVersion.getName());
    VersionIdentifier compareMacArm = VersionIdentifier.of("2.24.0");

    String baseUrl = "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_";

    doAddVersion(urlVersion, baseUrl + "windows_amd64.zip", WINDOWS, X64);
    doAddVersion(urlVersion, baseUrl + "linux_amd64.tar.gz", LINUX, X64);
    doAddVersion(urlVersion, baseUrl + "linux_arm64.tar.gz", LINUX, ARM64);
    doAddVersion(urlVersion, baseUrl + "macOS_amd64.tar.gz", MAC, X64);
    if (vid.compareVersion(compareMacArm).isGreater())
      doAddVersion(urlVersion, baseUrl + "macOS_arm64.tar.gz", MAC, ARM64);

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
