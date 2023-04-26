package com.devonfw.tools.ide.url.updater.graalvm;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for GraalVM.
 */
public class GraalVmUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "graalvm";
  }

  @Override
  protected String mapVersion(String version) {

    return version.replaceAll("vm-", "");
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-";
    doUpdateVersion(urlVersion, baseUrl + "windows-amd64-${version}.zip", WINDOWS, X64);
    doUpdateVersion(urlVersion, baseUrl + "linux-amd64-${version}.tar.gz", LINUX, X64);
    doUpdateVersion(urlVersion, baseUrl + "darwin-amd64-${version}.tar.gz", MAC, X64);
    doUpdateVersion(urlVersion, baseUrl + "darwin-aarch64-${version}.tar.gz", MAC, ARM64);

  }

  @Override
  protected String getGithubOrganization() {

    return "graalvm";
  }

  @Override
  protected String getGithubRepository() {

    return "graalvm-ce-builds";
  }
}
