package com.devonfw.tools.ide.url.updater.python;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} of Python.
 */
public class PythonUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getTool() {

    return "python";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://www.python.org/ftp/python/${version}/python-${version}";
    doUpdateVersion(urlVersion, baseUrl + "-embed-win32.zip", WINDOWS);
    doUpdateVersion(urlVersion, "https://www.python.org/ftp/python/${version}/Python-${version}" + ".tgz");
  }

  @Override
  protected String getVersionUrl() {

    return "https://www.python.org/ftp/python/";

  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(3\\.\\d+\\.\\d+)");
  }
}
