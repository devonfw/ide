package com.devonfw.tools.ide.url.Updater.java;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class TemurinCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return null;
    }

    @Override
    protected String getToolName() {
        return "Java";
    }

    @Override
    protected String getEdition() {
        return "Temurin";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {

    }

    @Override
    protected Set<String> getVersions() {
        return null;
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/adoptium/temurin${major}-binaries/releases";
    }

    @Override
    protected List<String> getDownloadUrls() {
        return null;
    }

    @Override
    protected Mappings getMappings() {
        return null;
    }
}

