package com.devonfw.tools.ide.url.updater.az;

import com.devonfw.tools.ide.github.GithubTag;
import com.devonfw.tools.ide.github.GithubTags;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;
import com.devonfw.tools.ide.version.VersionIdentifier;

import java.util.Collection;

/**
 * {@link GithubUrlUpdater} for Azure-CLI.
 */
public class AzureUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "azure";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    doAddVersion(urlVersion, "https://azcliprod.blob.core.windows.net/msi/azure-cli-${version}.msi");
  }

  @Override
  protected String getGithubOrganization() {

    return "Azure";
  }

  @Override
  protected String getGithubRepository() {

    return "azure-cli";
  }

  @Override
  protected String mapVersion(String version) {

    version = version.substring(version.lastIndexOf("-") + 1);
    VersionIdentifier vid = VersionIdentifier.of(version);
    if (vid.isValid() && isVersionGreaterThan(version, "2.17.0"))
      return super.mapVersion(version);
    else return null;
  }
}
