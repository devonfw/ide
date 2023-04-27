package com.devonfw.tools.ide.url.updater.gcviewer;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for GCViewer.
 */
public class GcViewerUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "gcviewer";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://sourceforge.net/projects/gcviewer/files/gcviewer-${version}.jar");
  }

  @Override
  protected String getGithubOrganization() {

    return "chewiebug";
  }

  @Override
  protected String getGithubRepository() {

    return "GCViewer";
  }
}
