package com.devonfw.tools.ide.url.Updater.cobigen;

import com.devonfw.tools.ide.url.Updater.MavenCrawler;


public class CobigenCrawler extends MavenCrawler {
    @Override
    protected String getToolName() {
        return "cobigen";
    }

    @Override
    protected String getArtifcatId() {
        return "cli";
    }

    @Override
    protected String getGroupIdPath() {
        return "com/devonfw/cobigen";
    }
}
