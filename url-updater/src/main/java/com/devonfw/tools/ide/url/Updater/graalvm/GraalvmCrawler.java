package com.devonfw.tools.ide.url.Updater.graalvm;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GraalvmCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("([0-9]+\\.[0-9]+\\.[0-9]+[0-9]*)");
    }

    @Override
    protected String getToolName() {
        return "graalvm";
    }

    @Override
    protected String getEdition() {
        return "graalvm";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/graalvm/graalvm-ce-builds/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-${os}-${arch}-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSTypes.WINDOWS, "zip");
        mappings.extensions.put(OSTypes.LINUX, "tar.gz");
        mappings.extensions.put(OSTypes.MAC, "tar.gz");
        mappings.oses.put(OSTypes.WINDOWS, "windows");
        mappings.oses.put(OSTypes.LINUX, "linux");
        mappings.oses.put(OSTypes.MAC, "darwin");
        mappings.architectures.put("x86_64", "amd64");
        return mappings;
    }
}
