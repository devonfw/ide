package com.devonfw.tools.ide.url.Updater.gcviewer;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class GCViewerCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "gcviewer";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://sourceforge.net/projects/gcviewer/files/gcviewer-${version}.jar");
    }
    @Override
    protected String getOrganizationName() {
        return "chewiebug";
    }

    @Override
    protected String getRepository() {
        return "GCViewer";
    }
}
