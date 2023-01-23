package com.devonfw.tools.ide.url.updater.sonarqube;

import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class SonarqubeCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "sonarqube";
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
