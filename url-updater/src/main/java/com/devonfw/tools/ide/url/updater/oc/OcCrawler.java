package com.devonfw.tools.ide.url.updater.oc;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

public class OcCrawler extends WebsiteCrawler {
  @Override
  protected String getToolName() {

    return "oc";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion,
        "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-windows-${version}.zip",
        OSType.WINDOWS);
    doUpdateVersion(urlVersion,
        "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-linux-${version}.tar.gz",
        OSType.LINUX);
    doUpdateVersion(urlVersion,
        "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-mac-${version}.tar.gz",
        OSType.MAC);

  }

  @Override
  protected String getVersionUrl() {

    return "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d\\.\\d\\.\\d*)");
  }
}
