package com.devonfw.tools.ide.url.updater.python;

import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class PythonCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "python";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://www.python.org/ftp/python/${version}/python-${version}-embed-win32.zip", OSType.WINDOWS);
        doUpdateVersion(urlVersion, "https://www.python.org/ftp/python/${version}/Python-${version}.tgz", OSType.LINUX);
        doUpdateVersion(urlVersion, "https://www.python.org/ftp/python/${version}/Python-${version}.tgz", OSType.MAC);


    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(3\\.\\d+\\.\\d+)");    }

    @Override
    protected String getVersionUrl() {
     return "https://www.python.org/ftp/python/";

    }
}
