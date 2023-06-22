package com.devonfw.tools.ide.url.updater.python;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} of Python.
 */
public class PythonUrlUpdater extends GithubUrlUpdater {

  private static final String BASE_URL = "https://www.python.org/ftp/python/${version}/";

  @Override
  protected String getTool() {

    return "python";
  }

  @Override
  protected String getVersionPrefixToRemove() {

    return "v";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    doAddVersion(urlVersion, BASE_URL + "python-${version}-embed-win32.zip", WINDOWS);
    doAddVersion(urlVersion, BASE_URL + "Python-${version}.tgz", MAC);
    doAddVersion(urlVersion, BASE_URL + "Python-${version}.tgz", LINUX);

  }

  @Override
  protected String getGithubOrganization() {

    return "python";
  }

  @Override
  protected String getGithubRepository() {

    return "cpython";
  }

  @Override
  protected String mapVersion(String version) {

    if (version.matches("v\\d+\\.\\d+\\.\\d+")) {
      return super.mapVersion(version);
    }
    else {
      return null;
    }
  }
}
