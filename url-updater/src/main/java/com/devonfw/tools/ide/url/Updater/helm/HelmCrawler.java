package com.devonfw.tools.ide.url.Updater.helm;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HelmCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("([0-9]+\\.[0-9]+\\.[0-9]+)");
    }

    @Override
    protected String getToolName() {
        return "helm";
    }

    @Override
    protected String getEdition() {
        return "helm";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/helm/helm/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://get.helm.sh/helm-${version}-${os}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSTypes.WINDOWS, "zip");
        mappings.extensions.put(OSTypes.LINUX, "tar.gz");
        mappings.extensions.put(OSTypes.MAC, "tar.gz");
        mappings.oses.put(OSTypes.WINDOWS, "windows-amd64");
        mappings.oses.put(OSTypes.LINUX, "linux-amd64");
        mappings.oses.put(OSTypes.MAC, "darwin-amd64");
        return mappings;
    }
}
