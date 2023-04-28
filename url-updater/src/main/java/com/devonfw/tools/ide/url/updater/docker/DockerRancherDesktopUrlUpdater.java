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

    if (version.contains("test") || version.contains("tech-preview") || version.contains("beta")) {
      return null;
    }
    return version.replace("v", "");
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop";
    doAddVersion(urlVersion, baseUrl + ".Setup.${version}.exe", WINDOWS);
    doAddVersion(urlVersion, baseUrl + "-${version}.x86_64.dmg", MAC);
    doAddVersion(urlVersion, baseUrl + "-${version}.aarch64.dmg", MAC, ARM64);

  }
}
