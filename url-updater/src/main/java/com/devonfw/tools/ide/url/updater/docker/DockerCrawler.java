package com.devonfw.tools.ide.url.updater.docker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

public class DockerCrawler extends WebsiteCrawler {
  @Override
  protected String getToolName() {

    return "docker";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String version = urlVersion.getName().replaceAll("\\.", "");
    // get Code for version
    String body = doGetResponseBody("https://docs.docker.com/desktop/release-notes/");
    String regex = "<h2 id=\"" + version + "\".*?https://desktop\\.docker\\.com.*?(\\d{5})\\.exe(?!.<h2 id=)";
    Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
    Matcher matcher = pattern.matcher(body);
    String code;
    if (matcher.find()) {
      code = matcher.group(1);
      boolean success = doUpdateVersion(urlVersion,
          "https://desktop.docker.com/win/main/amd64/" + code + "/Docker%20Desktop%20Installer.exe", OSType.WINDOWS);
      if (!success) {
        return;
      }
      doUpdateVersion(urlVersion, "https://desktop.docker.com/mac/main/amd64/" + code + "/Docker.dmg", OSType.MAC,
          "amd64");
      doUpdateVersion(urlVersion, "https://desktop.docker.com/mac/main/arm64/" + code + "/Docker.dmg", OSType.MAC,
          "arm64");
    } else {
      // For the latest version, there is no code in the url.
      boolean success = doUpdateVersion(urlVersion,
          "https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe", OSType.WINDOWS);
      if (!success) {
        return;
      }
      doUpdateVersion(urlVersion, "https://desktop.docker.com/mac/main/amd64/Docker.dmg", OSType.MAC, "amd64");
      doUpdateVersion(urlVersion, "https://desktop.docker.com/mac/main/arm64/Docker.dmg", OSType.MAC, "arm64");
    }

  }

  @Override
  protected String getVersionUrl() {

    return "https://docs.docker.com/desktop/release-notes/";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(4\\.\\d+\\.\\d+)");
  }
}
