package com.devonfw.tools.ide.url.updater.vscode;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class VSCodeCrawler extends GithubCrawler {
  @Override
  protected String getToolName() {

    return "vscode";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://update.code.visualstudio.com/${version}/win32-x64-archive/stable",
        OSType.WINDOWS);
    doUpdateVersion(urlVersion, "https://update.code.visualstudio.com/${version}/linux-x64/stable", OSType.LINUX);
    doUpdateVersion(urlVersion, "https://update.code.visualstudio.com/${version}/darwin/stable", OSType.MAC);

  }

  @Override
  protected String getOrganizationName() {

    return "microsoft";
  }
}
