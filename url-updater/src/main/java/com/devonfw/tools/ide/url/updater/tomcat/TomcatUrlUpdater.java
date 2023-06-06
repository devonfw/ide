package com.devonfw.tools.ide.url.updater.tomcat;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} for Tomcat.
 */
public class TomcatUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getTool() {

    return "tomcat";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    doAddVersion(urlVersion, "https://archive.apache.org/dist/tomcat/tomcat-${major}/v${version}/src/apache-tomcat-${version}-src.${ext}");
  }

  @Override
  protected String getVersionUrl() {

    return "https://github.com/apache/tomcat/tags";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+(-[A-Z0-9]+)?");
  }
}
