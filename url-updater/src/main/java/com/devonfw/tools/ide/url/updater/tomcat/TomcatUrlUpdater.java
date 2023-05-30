package com.devonfw.tools.ide.url.updater.tomcat;

import java.util.regex.Pattern;

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

    doAddVersion(urlVersion,
//    "https://github.com/apache/tomcat/releases/tag/${version}");
//        "https://archive.apache.org/dist/tomcat/tomcat-10/${version}/src/apache-tomcat-${version}-src.tar.gz");
  }

  @Override
  protected String getVersionUrl() {

    return "https://tomcat.apache.org/whichversion.html";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+(-[A-Z0-9]+)?");
  }
}
