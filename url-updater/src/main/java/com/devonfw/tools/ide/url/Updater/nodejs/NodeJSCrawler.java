package com.devonfw.tools.ide.url.Updater.nodejs;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NodeJSCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(v\\d\\.\\d{2,3}\\.\\d{1,3})");
    }

    @Override
    protected String getToolName() {
        return "NodeJS";
    }

    @Override
    protected String getEdition() {
        return "NodeJS";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/nodejs/node/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://nodejs.org/dist/${version}/node-${version}-${os}-${arch}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.oses.put(OSTypes.WINDOWS, "win");
        mappings.oses.put(OSTypes.LINUX, "linux");
        mappings.oses.put(OSTypes.MAC, "darwin");
        mappings.architectures.put("arm64", "x64");
        mappings.architectures.put("aarch64", "arm64");
        mappings.extensions.put(OSTypes.WINDOWS, "zip");
        mappings.extensions.put(OSTypes.LINUX, "tar.gz");
        mappings.extensions.put(OSTypes.MAC, "tar.gz");
        return mappings;
    }
}