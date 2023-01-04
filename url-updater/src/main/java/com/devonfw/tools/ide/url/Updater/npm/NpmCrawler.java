package com.devonfw.tools.ide.url.Updater.npm;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class NpmCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d{1,2}\\.\\d+)");
    }

    @Override
    protected String getToolName() {
        return "npm";
    }

    @Override
    protected String getEdition() {
        return "npm";
    }

    @Override
    protected String getVersionUrl() {
        return "https://registry.npmjs.org/npm/";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://registry.npmjs.org/npm/-/npm-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSTypes.WINDOWS, "tgz");
        mappings.extensions.put(OSTypes.LINUX, "tgz");
        mappings.extensions.put(OSTypes.MAC, "tgz");
        return mappings;
    }
}
