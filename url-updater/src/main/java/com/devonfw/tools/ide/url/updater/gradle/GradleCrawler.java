package com.devonfw.tools.ide.url.updater.gradle;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

public class GradleCrawler extends WebsiteCrawler {
  @Override
  protected String getVersionUrl() {

    return "https://gradle.org/releases/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d\\.\\d[\\.\\d]*)");
  }

  @Override
  protected String getToolName() {

    return "gradle";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://services.gradle.org/distributions/gradle-${version}-bin.zip");

  }

}
