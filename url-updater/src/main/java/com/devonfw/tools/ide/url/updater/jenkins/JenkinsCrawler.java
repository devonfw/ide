package com.devonfw.tools.ide.url.updater.jenkins;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

public class JenkinsCrawler extends WebsiteCrawler {
  @Override
  protected String getVersionUrl() {

    return "https://mirrors.jenkins.io/war-stable/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d\\.\\d{2,3}\\.\\d)");

  }

  @Override
  protected String getToolName() {

    return "jenkins";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://mirrors.jenkins.io/war-stable/${version}/jenkins.war");

  }
}
