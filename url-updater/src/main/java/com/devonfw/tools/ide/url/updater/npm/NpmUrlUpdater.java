package com.devonfw.tools.ide.url.updater.npm;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * {@link WebsiteUrlUpdater} for npm (node package manager).
 */
public class NpmUrlUpdater extends WebsiteUrlUpdater {
  @Override
  protected String getTool() {

    return "npm";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String baseUrl = "https://nodejs.org/dist/v${version}/node-v${version}";
    doAddVersion(urlVersion, baseUrl + "-win-x64.zip", WINDOWS, X64);
    doAddVersion(urlVersion, baseUrl + "-darwin-x64.tar.gz", MAC, X64);
    doAddVersion(urlVersion, baseUrl + ".pkg", MAC, ARM64);
    doAddVersion(urlVersion, baseUrl + "-linux-x64.tar.xz", LINUX, X64);
  }

  @Override
  protected String getVersionUrl() {

    return "https://registry.npmjs.org/npm/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d\\.\\d{1,2}\\.\\d+)");
  }
}
