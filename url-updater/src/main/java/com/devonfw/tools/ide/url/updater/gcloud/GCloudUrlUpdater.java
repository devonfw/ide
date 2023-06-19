package com.devonfw.tools.ide.url.updater.gcloud;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;
import com.devonfw.tools.ide.version.VersionIdentifier;

import java.util.regex.Pattern;

public class GCloudUrlUpdater extends GithubUrlUpdater {

  private static final VersionIdentifier MIN_GCLOUD_VID = VersionIdentifier.of("200.0.0");

  @Override
  protected String getTool() {

    return "google-cloud-sdk";
  }

  @Override
  protected String getGithubOrganization() {

    return "twistedpair";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    VersionIdentifier vid = urlVersion.getVersionIdentifier();

    if (vid.compareVersion(MIN_GCLOUD_VID).isGreater()) {
      String baseUrl = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-${version}-";

      doAddVersion(urlVersion, baseUrl + "windows-x86_64.zip", WINDOWS);
      doAddVersion(urlVersion, baseUrl + "linux-x86_64.tar.gz", LINUX);
      doAddVersion(urlVersion, baseUrl + "linux-arm.tar.gz", LINUX, ARM64);
      doAddVersion(urlVersion, baseUrl + "darwin-x86_64.tar.gz", MAC);
      doAddVersion(urlVersion, baseUrl + "darwin-arm.tar.gz", MAC, ARM64);

    }
  }

}
