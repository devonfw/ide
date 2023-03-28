package com.devonfw.tools.ide.url.updater.kotlin;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

import java.util.regex.Pattern;

public class KotlinNativeCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "kotlin";
    }

    @Override
    protected String getEdition() {
        return "kotlinnative";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://github.com/JetBrains/kotlin/releases/download/v${version}/kotlin-native-windows-x86_64-${version}.zip", OSType.WINDOWS,"x64");
        doUpdateVersion(urlVersion, "https://github.com/JetBrains/kotlin/releases/download/v${version}/kotlin-native-linux-x86_64-${version}.tar.gz", OSType.LINUX,"x64");
        doUpdateVersion(urlVersion, "https://github.com/JetBrains/kotlin/releases/download/v${version}/kotlin-native-macos-x86_64-${version}.tar.gz", OSType.MAC,"x64");
        doUpdateVersion(urlVersion, "https://github.com/JetBrains/kotlin/releases/download/v${version}/kotlin-native-macos-aarch64-${version}.tar.gz", OSType.MAC,"arm64");
    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+");
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/JetBrains/kotlin/releases";
    }
}
