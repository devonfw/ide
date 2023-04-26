package com.devonfw.tools.ide.url.updater.intellij;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;

/**
 * {@link IntellijUrlUpdater} for the ultimate edition of IntelliJ (commercial, requires a license).
 */
public class IntellijUltimateUrlUpdater extends IntellijUrlUpdater {

  @Override
  protected String getEdition() {

    return "ultimate";
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.exe", OperatingSystem.WINDOWS);
    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.tar.gz", OperatingSystem.LINUX);
    doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.dmg", OperatingSystem.MAC);
  }
}
