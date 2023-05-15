package com.devonfw.tools.ide.url.updater.androidstudio;

import java.util.Collection;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.JsonUrlUpdater;

/**
 * {@link JsonUrlUpdater} for Android Studio.
 */
public class AndroidStudioUrlUpdater extends JsonUrlUpdater<AndroidJsonObject> {

  /** The base URL for the download of the software */
  private final static String DOWNLOAD_BASE_URL = "https://redirector.gvt1.com";

  /** The base URL for the version json file */
  private final static String VERSION_BASE_URL = "https://jb.gg";

  /** The path of the download URL */
  private final static String DOWNLOAD_URL_PATH = "edgedl/android/studio/ide-zips";

  /** The name of the version json file */
  private final static String VERSION_FILENAME = "android-studio-releases-list.json";

  /**
   * @return String of download base URL
   */
  protected String getDownloadBaseUrl() {
    return DOWNLOAD_BASE_URL;
  }

  /**
   * @return String of version base URL
   */
  protected String getVersionBaseUrl() {
    return VERSION_BASE_URL;
  }

  @Override
  protected String getTool() {

    return "android-studio";
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    String version = urlVersion.getName();

    String versionDownloadUrl = getDownloadBaseUrl() + "/" + DOWNLOAD_URL_PATH + "/" + version + "/" + "android-studio" + "-" + version + "-";

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

    return getVersionBaseUrl() + "/" + VERSION_FILENAME;
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
