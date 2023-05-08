package com.devonfw.tools.ide.url.updater.androidstudio;

import java.util.Collection;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.JsonUrlUpdater;

/**
 * {@link JsonUrlUpdater} for Android Studio.
 */
public class AndroidStudioUrlUpdater extends JsonUrlUpdater<AndroidJsonObject> {

  /** The download URL */
  private final static String DOWNLOAD_URL = "https://redirector.gvt1.com/edgedl/android/studio/ide-zips";

  /** The version URL */
  private final static String VERSION_URL = "https://jb.gg/android-studio-releases-list.json";

  @Override
  protected String getTool() {

    return "android-studio";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String version = urlVersion.getName();

    String versionDownloadUrl = DOWNLOAD_URL + "/" + version + "/" + "android-studio" + "-" + version + "-";

    String downloadUrlWindows = versionDownloadUrl + "windows.zip";
    String downloadUrlLinux = versionDownloadUrl + "linux.tar.gz";
    String downloadUrlMac = versionDownloadUrl + "mac.zip";
    String downloadUrlMacArm64 = versionDownloadUrl + "mac_arm.zip";

    doAddVersion(urlVersion, downloadUrlWindows, WINDOWS);
    doAddVersion(urlVersion, downloadUrlLinux, LINUX);
    doAddVersion(urlVersion, downloadUrlMac, MAC);
    doAddVersion(urlVersion, downloadUrlMacArm64, MAC, ARM64);

  }

  @Override
  protected String doGetVersionUrl() {

    return VERSION_URL;
  }

  @Override
  protected Class<AndroidJsonObject> getJsonObjectType() {

    return AndroidJsonObject.class;
  }

  @Override
  protected void collectVersionsFromJson(AndroidJsonObject jsonItem, Collection<String> versions) {

    AndroidJsonContent content = jsonItem.getContent();
    for (AndroidJsonItem item : content.getItem()) {
      String version = item.getVersion();
      addVersion(version, versions);
    }
  }
}
