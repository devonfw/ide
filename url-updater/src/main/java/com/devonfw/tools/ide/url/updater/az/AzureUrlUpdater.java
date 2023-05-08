package com.devonfw.tools.ide.url.updater.az;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

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
}
