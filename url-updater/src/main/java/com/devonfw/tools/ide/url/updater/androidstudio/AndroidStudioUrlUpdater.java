package com.devonfw.tools.ide.url.updater.androidstudio;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;

import java.util.regex.Pattern;

/**
 * Abstract {@link WebsiteUrlUpdater} base class for Android Studio.
 */
public class AndroidStudioUrlUpdater extends WebsiteUrlUpdater {

  private final static String DOWNLOAD_URL = "https://redirector.gvt1.com/edgedl/android/studio/ide-zips";

  @Override
  protected String getTool() {

    return "android-studio";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String version = urlVersion.getName();
    //    String mac = "https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2023.1.1.1/android-studio-2023.1.1.1-mac.zip";
    //    String linux = "https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2023.1.1.1/android-studio-2023.1.1.1-linux.tar.gz";

    String baseUrlWindows = DOWNLOAD_URL + "/" + version + "/" + "android-studio-" + version + "-" + "windows.zip";
    String baseUrlLinux = DOWNLOAD_URL + "/" + version + "/" + "android-studio-" + version + "-" + "linux.tar.gz";
    String baseUrlMac = DOWNLOAD_URL + "/" + version + "/" + "android-studio-" + version + "-" + "mac.zip";

    doAddVersion(urlVersion, baseUrlWindows, WINDOWS);
  }

  @Override
  protected String getVersionUrl() {

    return "https://developer.android.com/studio/archive";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d{4}\\.\\d\\.\\d\\.\\d)");
  }
}
