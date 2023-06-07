package com.devonfw.tools.ide.url.updater.gcloud;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

import java.util.regex.Pattern;

public class GCloudUrlUpdater extends WebsiteUrlUpdater {

  @Override
  protected String getTool() {

    return "gcloud";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-${version}-${os}-";

    doAddVersion(urlVersion, baseUrl + "x86_64.zip", WINDOWS);
    //TODO add Linux and MacOS
  }

  @Override
  protected String getVersionUrl() {

    return "https://cloud.google.com/sdk/docs/release-notes";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
  }

  @Override
  protected String mapVersion(String version) {

    if (version.matches("\\d{3,}\\.\\d+\\.\\d+")) {
      return super.mapVersion(version);
    } else {
      return null;
    }
  }
}
