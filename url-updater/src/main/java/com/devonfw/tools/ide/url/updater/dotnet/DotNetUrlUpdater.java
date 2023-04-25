package com.devonfw.tools.ide.url.updater.dotnet;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for Microsoft .NET core.
 */
public class DotNetUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "dotnet";
  }

  @Override
  protected String mapVersion(String version) {

    return version.replace("v", "");
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-";
    boolean ok1 = doUpdateVersion(urlVersion, baseUrl + "win-x64.exe", WINDOWS, X64);
    boolean ok2 = doUpdateVersion(urlVersion, baseUrl + "win-arm64.exe", WINDOWS, ARM64);
    if (!ok1 && !ok2) {
      return;
    }
    doUpdateVersion(urlVersion, baseUrl + "linux-x64.tar.gz", LINUX, X64);
    doUpdateVersion(urlVersion, baseUrl + "linux-arm64.tar.gz", LINUX, ARM64);
    doUpdateVersion(urlVersion, baseUrl + "osx-x64.tar.gz", MAC, X64);
    doUpdateVersion(urlVersion, baseUrl + "osx-arm64.tar.gz", MAC, ARM64);
  }

  @Override
  protected String getGithubOrganization() {

    return "dotnet";
  }

  @Override
  protected String getGithubRepository() {

    return "sdk";
  }
}
