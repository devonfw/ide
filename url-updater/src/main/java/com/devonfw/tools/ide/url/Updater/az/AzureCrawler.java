package com.devonfw.tools.ide.url.Updater.az;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AzureCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
    }

    @Override
    protected String getToolName() {
        return "azure";
    }

    @Override
    protected String getEdition() {
        return "azure";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/Azure/azure-cli/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://azcliprod.blob.core.windows.net/msi/azure-cli-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        HashMap<OSTypes,String> extensions = new HashMap<>();
        extensions.put(OSTypes.WINDOWS, "msi");
        Mappings mappings = new Mappings();
        mappings.extensions = extensions;
        return mappings;
    }
}
