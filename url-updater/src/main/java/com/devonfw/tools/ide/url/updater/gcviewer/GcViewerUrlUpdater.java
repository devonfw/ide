package com.devonfw.tools.ide.url.updater.gcviewer;

import com.devonfw.tools.ide.github.GithubTag;
import com.devonfw.tools.ide.github.GithubTags;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

import java.util.Collection;

/**
 * {@link GithubUrlUpdater} for GCViewer.
 */
public class GcViewerUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "gcviewer";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    doAddVersion(urlVersion, "https://sourceforge.net/projects/gcviewer/files/gcviewer-${version}.jar/download");
  }

  @Override
  protected String getGithubOrganization() {

    return "chewiebug";
  }

  @Override
  protected String getGithubRepository() {

    return "GCViewer";
  }

  @Override
  protected void collectVersionsFromJson(GithubTags jsonItem, Collection<String> versions) {

    for (GithubTag item : jsonItem) {
      String version = item.getRef().replace("refs/tags/", "");
      if(version.matches("\\d+\\.\\d+(\\.\\d+)?"))
        addVersion(version, versions);
    }
  }
}
