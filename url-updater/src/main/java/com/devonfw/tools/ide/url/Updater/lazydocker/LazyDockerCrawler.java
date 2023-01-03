package com.devonfw.tools.ide.url.Updater.lazydocker;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LazyDockerCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d{2,3}[\\.\\d]*)");
    }

    @Override
    protected String getToolName() {
        return "Docker";
    }

    @Override
    protected String getEdition() {
        return "LazyDocker";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/jesseduffield/lazydocker/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_${os}_${arch}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.oses.put(OSTypes.WINDOWS, "Windows");
        mappings.oses.put(OSTypes.LINUX, "Linux");
        mappings.oses.put(OSTypes.MAC, "Darwin");
        mappings.architectures.put("arm64", "arm64");
        mappings.architectures.put("x64", "x86_64");
        mappings.extensions.put(OSTypes.WINDOWS, "zip");
        mappings.extensions.put(OSTypes.LINUX, "tar.gz");
        mappings.extensions.put(OSTypes.MAC, "tar.gz");
        return mappings;
    }
}
