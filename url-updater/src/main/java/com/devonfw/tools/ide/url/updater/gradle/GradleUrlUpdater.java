package com.devonfw.tools.ide.url.updater.gradle;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} for Gradle.
 */
public class GradleUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getVersionUrl() {

    return "https://gradle.org/releases/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d\\.\\d[\\.\\d]*)");
  }

  @Override
  protected String getTool() {

    return "gradle";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://services.gradle.org/distributions/gradle-${version}-bin.zip");

  }

}
