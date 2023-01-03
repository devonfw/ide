package com.devonfw.tools.ide.url.Updater.cobigen;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CobigenUpdater extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return null;
    }

    @Override
    protected String getToolName() {
        return "cobigen";
    }

    @Override
    protected String getEdition() {
        return "cobigen";
    }

    @Override
    protected String getVersionUrl() {
        return "https://repo1.maven.org/maven2/com/devonfw/cobigen/cli/maven-metadata.xml";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://repo1.maven.org/maven2/com/devonfw/cobigen/cli/${version}/cli-${version}.jar");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        return new Mappings();
    }
}
