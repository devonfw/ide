package com.devonfw.tools.ide.url.updater.cobigen;

import com.devonfw.tools.ide.url.updater.MavenCrawler;


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
