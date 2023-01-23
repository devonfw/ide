package com.devonfw.tools.ide.url.updater.intellij;

import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class IntelliJUltimateEditionCrawler extends IntelliJCrawler {
    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.exe", OSType.WINDOWS);
        doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.tar.gz", OSType.LINUX);
        doUpdateVersion(urlVersion, "https://download.jetbrains.com/idea/ideaIU-${version}.dmg", OSType.MAC);
    }
}
