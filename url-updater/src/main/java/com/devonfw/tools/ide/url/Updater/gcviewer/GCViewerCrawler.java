package com.devonfw.tools.ide.url.Updater.gcviewer;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GCViewerCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d+\\.\\d+(?:\\.\\d+)?)");
    }

    @Override
    protected String getToolName() {
        return "gcviewer";
    }

    @Override
    protected String getEdition() {
        return "gcviewer";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/chewiebug/GCViewer/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://sourceforge.net/projects/gcviewer/files/gcviewer-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSTypes.WINDOWS, "jar");
        mappings.extensions.put(OSTypes.LINUX, "jar");
        mappings.extensions.put(OSTypes.MAC, "jar");
        return mappings;
    }
}
