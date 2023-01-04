package com.devonfw.tools.ide.url.Updater.vscode;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class VSCodeCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("refs\\/tags\\/(\\d+\\.\\d+\\.\\d+)");
    }

    @Override
    protected String getToolName() {
        return "vscode";
    }

    @Override
    protected String getEdition() {
        return "vscode";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/microsoft/vscode/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://update.code.visualstudio.com/${version}/${os}/stable");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        HashMap<OSTypes, String> oses = new HashMap<>();
        oses.put(OSTypes.WINDOWS, "win32-x64-archive");
        oses.put(OSTypes.LINUX, "linux-x64");
        oses.put(OSTypes.MAC, "darwin");
        return new Mappings(oses);
    }
}
