package com.devonfw.tools.ide.url.updater.lazydocker;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for lazydocker.
 */
public class LazyDockerUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "lazydocker";
  }

  @Override
  protected String getGithubOrganization() {

    return "jesseduffield";
  }

  @Override
  protected String getGithubRepository() {

    return "lazydocker";
  }

  @Override
  protected String getVersionPrefixToRemove() {

    return "v";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_";
    doAddVersion(urlVersion, baseUrl + "Windows_x86_64.zip", WINDOWS, X64);
    doAddVersion(urlVersion, baseUrl + "Windows_arm64.zip", WINDOWS, ARM64);
    doAddVersion(urlVersion, baseUrl + "Linux_x86_64.tar.gz", LINUX, X64);
    doAddVersion(urlVersion, baseUrl + "Linux_arm64.tar.gz", LINUX, ARM64);
    doAddVersion(urlVersion, baseUrl + "Darwin_x86_64.tar.gz", MAC, X64);
    doAddVersion(urlVersion, baseUrl + "Darwin_arm64.tar.gz", MAC, ARM64);
  }

}
