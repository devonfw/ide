package com.devonfw.tools.ide.url.updater.dotnet;

import com.devonfw.tools.ide.github.GithubTag;
import com.devonfw.tools.ide.github.GithubTags;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;
import com.devonfw.tools.ide.version.VersionIdentifier;

import java.util.Collection;

/**
 * {@link GithubUrlUpdater} for Microsoft .NET core.
 */
public class DotNetUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "dotnet";
  }

  @Override
  protected String getVersionPrefixToRemove() {

    return "v";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-";
    boolean ok1 = doAddVersion(urlVersion, baseUrl + "win-x64.exe", WINDOWS, X64);
    boolean ok2 = doAddVersion(urlVersion, baseUrl + "win-arm64.exe", WINDOWS, ARM64);
    if (!ok1 && !ok2) {
      return;
    }
    doAddVersion(urlVersion, baseUrl + "linux-x64.tar.gz", LINUX, X64);
    doAddVersion(urlVersion, baseUrl + "linux-arm64.tar.gz", LINUX, ARM64);
    doAddVersion(urlVersion, baseUrl + "osx-x64.tar.gz", MAC, X64);
    doAddVersion(urlVersion, baseUrl + "osx-arm64.tar.gz", MAC, ARM64);
  }

  @Override
  protected String getGithubOrganization() {

    return "dotnet";
  }

  @Override
  protected String getGithubRepository() {

    return "sdk";
  }

  @Override
  protected void collectVersionsFromJson(GithubTags jsonItem, Collection<String> versions) {

    for (GithubTag item : jsonItem) {
      String version = item.getRef().replace("refs/tags/", "");
      if(version.matches("v\\d+\\.\\d+\\.\\d+"))
        addVersion(version, versions);
    }
  }
}
