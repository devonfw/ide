package com.devonfw.tools.ide.url.Updater.aws;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.*;
import java.util.regex.Pattern;


public class AWSCrawler extends WebsiteVersionCrawler {

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]*");
    }

    @Override
    protected String getToolName() {
        return "aws";
    }

    @Override
    protected String getEdition() {
        return "aws";
    }

    @Override
    protected String getVersionUrl() {
        return "https://api.github.com/repos/aws/aws-cli/git/refs/tags";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://awscli.amazonaws.com/${os}-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        HashMap<OSTypes, String> oses = new HashMap<>();
        oses.put(OSTypes.WINDOWS, "AWSCLIV2");
        oses.put(OSTypes.LINUX, "awscli-exe-linux-x86_64");
        oses.put(OSTypes.MAC, "AWSCLIV2");
        HashMap<String, String> architectures = new HashMap<>();
        architectures.put("x64", "x64");
        HashMap<OSTypes, String> extension = new HashMap<>();
        extension.put(OSTypes.WINDOWS, "msi");
        extension.put(OSTypes.LINUX, "zip");
        extension.put(OSTypes.MAC, "pkg");
        return new Mappings(oses, architectures, extension);
    }
}

