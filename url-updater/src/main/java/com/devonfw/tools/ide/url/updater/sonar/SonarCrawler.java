package com.devonfw.tools.ide.url.updater.sonar;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;

public class SonarCrawler extends GithubCrawler {
  @Override
  protected String getToolName() {

    return "sonar";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-${version}.zip");
  }

  @Override
  protected String getOrganizationName() {

    return "SonarSource";
  }

  @Override
  protected String getRepository() {

    return "sonarqube";
  }
}
