package com.devonfw.tools.ide.url.updater.npm;

import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class NpmCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "npm";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
    doUpdateVersion(urlVersion, "https://nodejs.org/dist/v${version}/node-v${version}-win-x64.zip", OSType.WINDOWS,"x64");
    doUpdateVersion(urlVersion, "https://nodejs.org/dist/v${version}/node-v${version}-darwin-x64.tar.gz", OSType.MAC,"x64");
    doUpdateVersion(urlVersion, "https://nodejs.org/dist/v${version}/node-v${version}.pkg", OSType.MAC,"arm64");
    doUpdateVersion(urlVersion, "https://nodejs.org/dist/v${version}/node-v${version}-linux-x64.tar.xz", OSType.LINUX,"x64");
    }


    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d{1,2}\\.\\d+)");
    }

    @Override
    protected String getVersionUrl() {
        return "https://registry.npmjs.org/npm/";
    }
}
