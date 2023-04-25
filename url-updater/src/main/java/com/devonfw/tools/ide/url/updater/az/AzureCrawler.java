package com.devonfw.tools.ide.url.updater.az;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;

public class AzureCrawler extends GithubCrawler {
  @Override
  protected String getToolName() {

    return "Azure";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://azcliprod.blob.core.windows.net/msi/azure-cli-${version}.msi");
  }

  @Override
  protected String getOrganizationName() {

    return "Azure";
  }

  @Override
  protected String getRepository() {

    return "azure-cli";
  }
}
