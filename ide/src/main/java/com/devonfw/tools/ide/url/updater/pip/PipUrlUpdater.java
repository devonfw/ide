package com.devonfw.tools.ide.url.updater.pip;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} for pip (python installer of pacakges).
 */
public class PipUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getTool() {

    return "pip";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    doAddVersion(urlVersion, "https://bootstrap.pypa.io/pip/${version}/get-pip.py");
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
