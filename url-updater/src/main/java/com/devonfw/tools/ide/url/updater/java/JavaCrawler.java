package com.devonfw.tools.ide.url.updater.java;

import java.util.Collection;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.JsonCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class JavaCrawler extends JsonCrawler<JavaJsonObject> {
  @Override
  protected String getToolName() {

    return "java";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://github.com/adoptium/temurin";
    String version = urlVersion.getName();
    int i = 0;
    int length = version.length();
    while (i < length) {
      char c = version.charAt(i);
      if (c >= '0' && c <= '9') {
        i++;
      } else {
        break;
      }
    }
    String major = version.substring(0, i);
    String code;
    if (version.charAt(0) == '8') {
      code = "jdk" + version.replace("b", "-b");
    } else {
      code = "jdk-" + version.replace("_", "%2B");
    }

    boolean success = doUpdateVersion(urlVersion, baseUrl + major + "-binaries/releases/download/" + code + "/OpenJDK"
        + major + "U-jdk_x64_windows_hotspot_${version}.zip", OSType.WINDOWS);
    if (!success) {
      baseUrl = "https://github.com/AdoptOpenJDK/openjdk";
      success = doUpdateVersion(urlVersion, baseUrl + major + "-binaries/releases/download/" + code + "/OpenJDK" + major
          + "U-jdk_x64_windows_hotspot_${version}.zip", OSType.WINDOWS);
      if (!success) {
        return;
      }
    }
    doUpdateVersion(urlVersion, baseUrl + major + "-binaries/releases/download/" + code + "/OpenJDK" + major
        + "U-jdk_x64_mac_hotspot_${version}.tar.gz", OSType.MAC);
    doUpdateVersion(urlVersion, baseUrl + major + "-binaries/releases/download/" + code + "/OpenJDK" + major
        + "U-jdk_x64_linux_hotspot_${version}.tar.gz", OSType.LINUX);

  }

  @Override
  protected String doGetVersionUrl() {

    return "https://api.adoptium.net/v3/info/release_versions?architecture=x64&heap_size=normal&image_type=jdk&jvm_impl=hotspot&page=0&page_size=50&project=jdk&release_type=ga&sort_method=DEFAULT&sort_order=DESC&vendor=eclipse";
  }

  @Override
  protected Class<JavaJsonObject> getJsonObjectType() {

    return JavaJsonObject.class;
  }

  @Override
  protected void collectVersionsFromJson(JavaJsonObject jsonItem, Collection<String> versions) {

    for (JavaJsonVersion item : jsonItem.getVersions()) {
      String version = item.getOpenjdk_version();
      version = version.replace("+", "_");
      // replace 1.8.0_ to 8u
      if (version.startsWith("1.8.0_")) {
        version = "8u" + version.substring(6);
        version = version.replace("-b", "b");
      }
      versions.add(version);

    }

  }
}
