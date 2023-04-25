package com.devonfw.tools.ide.url.updater.oc;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} for oc (openshift CLI).
 */
public class OcUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getTool() {

    return "oc";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-";
    doUpdateVersion(urlVersion, baseUrl + "windows-${version}.zip", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "linux-${version}.tar.gz", LINUX);
    doUpdateVersion(urlVersion, baseUrl + "mac-${version}.tar.gz", MAC);

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
