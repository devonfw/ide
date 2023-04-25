package com.devonfw.tools.ide.url.updater.quarkus;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class QuarkusCrawler extends GithubCrawler {
  @Override
  protected String getToolName() {

    return "quarkus";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion,
        "https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}.zip", OSType.WINDOWS);
    doUpdateVersion(urlVersion,
        "https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}.tar.gz",
        OSType.LINUX);
    doUpdateVersion(urlVersion,
        "https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}.tar.gz", OSType.MAC);
  }

  @Override
  protected String getOrganizationName() {

    return "quarkusio";
  }

  @Override
  protected String getRepository() {

    return "quarkus";
  }
}