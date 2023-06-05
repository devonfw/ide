package com.devonfw.tools.ide.url.updater.eclipse;

import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

/**
 * Abstract {@link WebsiteUrlUpdater} base-class for eclipse editions.
 */
public abstract class EclipseUrlUpdater extends WebsiteUrlUpdater {

  private static final String[] MIRRORS = { "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads",
  "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads",
  "https://archive.eclipse.org/technology/epp/downloads" };

  @Override
  protected String getTool() {

    return "eclipse";
  }

  /**
   * @return the eclipse edition name.
   */
  protected String getEclipseEdition() {

    return getEdition();
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    // archive
    String version = urlVersion.getName();
    String releaseType = "R";
    int lastDash = version.lastIndexOf('-');
    if (lastDash > 0) {
      String lastSegment = version.substring(lastDash + 1);
      if (lastSegment.length() >= 2) {
        char first = lastSegment.charAt(0);
        if (Character.isLetter(first)) {
          char last = lastSegment.charAt(lastSegment.length() - 1);
          if (Character.isDigit(last)) {
            // found a non release type (e.g. M1, M2, SR1, ...)
            releaseType = lastSegment;
            version = version.substring(0, lastDash);
          }
        }
      }
    }
    String edition = getEclipseEdition();
    for (String mirror : MIRRORS) {
      String baseUrl = mirror + "/release/" + version + "/" + releaseType + "/eclipse-" + edition + "-" + version + "-"
          + releaseType + "-";
      doUpdateVersions(urlVersion, baseUrl);
    }
  }

  private boolean doUpdateVersions(UrlVersion urlVersion, String baseUrl) {

    boolean ok;
    ok = doAddVersion(urlVersion, baseUrl + "win32-x86_64.zip", WINDOWS, X64);
    if (!ok) {
      return false;
    }
    ok = doAddVersion(urlVersion, baseUrl + "linux-gtk-x86_64.tar.gz", LINUX, X64);
    ok = doAddVersion(urlVersion, baseUrl + "linux-gtk-aarch64.tar.gz", LINUX, ARM64);
    ok = doAddVersion(urlVersion, baseUrl + "macosx-cocoa-x86_64.tar.gz", MAC, X64);
    ok = doAddVersion(urlVersion, baseUrl + "macosx-cocoa-aarch64.tar.gz", MAC, ARM64);
    return ok;
  }

  @Override
  protected String getVersionUrl() {

    return "https://www.eclipse.org/downloads/packages/release";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("\\d{4}-\\d{2}(\\s\\w{2})?");
  }

  @Override
  protected String mapVersion(String version) {

    // TODO remove this hack and get versiosn from reliable API
    return super.mapVersion(version.replace(" ", "-"));
  }

}
