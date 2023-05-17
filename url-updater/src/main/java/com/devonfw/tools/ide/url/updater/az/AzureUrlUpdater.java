package com.devonfw.tools.ide.url.updater.az;

import com.devonfw.tools.ide.github.GithubTag;
import com.devonfw.tools.ide.github.GithubTags;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

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
  protected void collectVersionsFromJson(GithubTags jsonItem, Collection<String> versions) {

    for (GithubTag item : jsonItem) {
      String version = item.getRef().replace("refs/tags/", "");
      version = version.substring(version.lastIndexOf("-") + 1);
      if (version.matches("\\d+\\.\\d+\\.\\d+")) {
        if (doVersionMatchWithMinor(version, 2, 18))
          addVersion(version, versions);
      }
    }
  }
}
