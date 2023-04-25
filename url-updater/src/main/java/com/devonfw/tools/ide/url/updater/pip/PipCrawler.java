package com.devonfw.tools.ide.url.updater.pip;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

public class PipCrawler extends WebsiteCrawler {
  @Override
  protected String getToolName() {

    return "pip";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://bootstrap.pypa.io/pip/${version}/get-pip.py");
  }

  @Override
  protected String getVersionUrl() {

    return "https://bootstrap.pypa.io/pip/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d+\\.\\d(\\.\\d)?)");
  }
}
