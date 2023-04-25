package com.devonfw.tools.ide.url.updater.vscode;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for vscode (Visual Studio Code).
 */
public class VSCodeUrlUpdater extends GithubUrlUpdater {

  @Override
  protected String getTool() {

    return "vscode";
  }

  @Override
  protected String getGithubOrganization() {

    return "microsoft";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://update.code.visualstudio.com/${version}/";
    doUpdateVersion(urlVersion, baseUrl + "win32-x64-archive/stable", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "linux-x64/stable", LINUX);
    doUpdateVersion(urlVersion, baseUrl + "darwin/stable", MAC);

  }

}
