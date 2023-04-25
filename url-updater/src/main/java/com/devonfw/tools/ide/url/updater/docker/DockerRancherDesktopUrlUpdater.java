package com.devonfw.tools.ide.url.updater.docker;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

public class DockerRancherDesktopUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "docker";
  }

  @Override
  protected String getEdition() {

    return "rancher";
  }

  @Override
  protected String getGithubOrganization() {

    return "rancher-sandbox";
  }

  @Override
  protected String getGithubRepository() {

    return "rancher-desktop";
  }

  @Override
  protected String mapVersion(String version) {

    return version.replace("v", "");
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop";
    doUpdateVersion(urlVersion, baseUrl + ".Setup.${version}.exe", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "-${version}.x86_64.dmg", MAC);
    doUpdateVersion(urlVersion, baseUrl + "-${version}.aarch64.dmg", MAC, ARM64);

  }
}
