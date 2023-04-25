package com.devonfw.tools.ide.url.updater.cobigen;

import com.devonfw.tools.ide.url.updater.MavenBasedCrawler;

public class CobigenCrawler extends MavenBasedCrawler {
  @Override
  protected String getToolName() {

    return "cobigen";
  }

  @Override
  protected String getGroupIdPath() {

    return "com/devonfw/cobigen";
  }

  @Override
  protected String getArtifcatId() {

    return "cli";
  }
}
