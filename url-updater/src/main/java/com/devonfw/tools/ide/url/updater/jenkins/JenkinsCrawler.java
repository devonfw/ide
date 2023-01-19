package com.devonfw.tools.ide.url.updater.jenkins;

import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class JenkinsCrawler extends WebsiteCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d{2,3}\\.\\d)");

    }
    @Override
    protected String getToolName() {
        return "jenkins";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://mirrors.jenkins.io/war-stable/${version}/jenkins.war");

    }


    @Override
    protected String getVersionUrl() {
        return "https://mirrors.jenkins.io/war-stable/";
    }
}
