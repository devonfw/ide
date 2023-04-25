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
  protected String mapVersion(String version) {

    return version.replace("v", "");
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_";
    doUpdateVersion(urlVersion, baseUrl + "Windows_x86_64.zip", WINDOWS, X64);
    doUpdateVersion(urlVersion, baseUrl + "Windows_arm64.zip", WINDOWS, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "Linux_x86_64.tar.gz", LINUX, X64);
    doUpdateVersion(urlVersion, baseUrl + "Linux_arm64.tar.gz", LINUX, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "Darwin_x86_64.tar.gz", MAC, X64);
    doUpdateVersion(urlVersion, baseUrl + "Darwin_arm64.tar.gz", MAC, ARM64);
  }

}
