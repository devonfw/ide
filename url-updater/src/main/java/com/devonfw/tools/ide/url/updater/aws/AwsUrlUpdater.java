package com.devonfw.tools.ide.url.updater.aws;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for AWS-CLI.
 */
public class AwsUrlUpdater extends GithubUrlUpdater {

  @Override
  protected String getTool() {

    return "aws";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    if (!urlVersion.getName().startsWith("2")) {
      return; // There are no valid download links for aws-cli below version 2
    }
    String baseUrl = "https://awscli.amazonaws.com/";
    boolean ok = doUpdateVersion(urlVersion, baseUrl + "AWSCLIV2-${version}.msi", OperatingSystem.WINDOWS);
    if (!ok) {
      return;
    }
    doUpdateVersion(urlVersion, baseUrl + "awscli-exe-linux-x86_64-${version}.zip", OperatingSystem.LINUX);
    doUpdateVersion(urlVersion, baseUrl + "AWSCLIV2-${version}.pkg", OperatingSystem.MAC);
  }

  @Override
  protected String getGithubOrganization() {

    return "aws";
  }

  @Override
  protected String getGithubRepository() {

    return "aws-cli";
  }
}
