package com.devonfw.tools.ide.url.updater.maven;

import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class MavenCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "maven";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion,"https://archive.apache.org/dist/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz");
    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d\\.\\d)");
    }

    @Override
    protected String getVersionUrl() {
        return "https://archive.apache.org/dist/maven/maven-3/";
    }
}
