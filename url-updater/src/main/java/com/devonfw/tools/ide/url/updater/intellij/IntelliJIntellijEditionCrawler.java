package com.devonfw.tools.ide.url.updater.intellij;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.OSType;

public class IntelliJIntellijEditionCrawler extends IntelliJCrawler {

  @Override
  protected String getEdition() {

    return "intellij";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIC-${version}.exe", OSType.WINDOWS);
    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIC-${version}.tar.gz", OSType.LINUX);
    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIC-${version}.dmg", OSType.MAC);
  }
}
