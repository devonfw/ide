package com.devonfw.tools.ide.url.Updater.quarkus;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class QuarkusCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d+\\.\\d+(?:\\.\\d+)?\\.Final)");
    }

    @Override
    protected String getToolName() {
        return "Quarkus";
    }

    @Override
    protected String getEdition() {
        return "Quarkus";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/quarkusio/quarkus/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://github.com/quarkusio/quarkus/releases/download/${version}/quarkus-cli-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSTypes.WINDOWS ,"zip");
        mappings.extensions.put(OSTypes.LINUX ,"tar.gz");
        mappings.extensions.put(OSTypes.MAC ,"tar.gz");
        return mappings;
    }
}
