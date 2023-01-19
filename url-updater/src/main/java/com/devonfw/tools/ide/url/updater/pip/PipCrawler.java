package com.devonfw.tools.ide.url.updater.pip;

import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.Objects;
import java.util.regex.Pattern;

public class PipCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "pip";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        if(Objects.equals(urlVersion.getName(), "22.3.1")){
            doUpdateVersion(urlVersion, "https://bootstrap.pypa.io/pip/get-pip.py");
        }
        doUpdateVersion(urlVersion, "https://bootstrap.pypa.io/pip/${version}/get-pip.py");
    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d+\\.\\d(\\.\\d)?)");
    }

    @Override
    protected String getVersionUrl() {
        return "https://bootstrap.pypa.io/pip/";
    }
}
