package com.devonfw.tools.ide.url.Updater.gh;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class GHCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "gh";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_windows_amd64.zip", OSType.WINDOWS,"x64");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_linux_amd64.tar.gz", OSType.LINUX,"x64");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_linux_arm64.tar.gz", OSType.LINUX,"arm64");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_linux_386.tar.gz", OSType.LINUX,"i386");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_macOS_arm64.tar.gz", OSType.MAC,"arm64");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_macOS_amd64.tar.gz", OSType.MAC,"x64");
        doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_macOS_386.tar.gz", OSType.MAC,"i386");


    }

    @Override
    protected String mapVersion(String version) {
        String version2= version.replaceAll("v", "");
        //filter out pre releases
        if(version2.contains("-")){
            return version2.substring(0, version2.indexOf("-"));
        }
        return version2;
    }
    //    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://github.com/cli/cli/releases/download/v${version}/gh_${version}_${os}_${arch}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.oses.put(OSType.WINDOWS, "windows");
//        mappings.oses.put(OSType.LINUX, "linux");
//        mappings.oses.put(OSType.MAC, "macOS");
//        mappings.architectures.put("arm64", "arm64");
//        mappings.architectures.put("i386", "386");
//        mappings.architectures.put("x86_64", "amd64");
//        mappings.extensions.put(OSType.WINDOWS, "zip");
//        mappings.extensions.put(OSType.LINUX, "tar.gz");
//        mappings.extensions.put(OSType.MAC, "tar.gz");
//        return mappings;
//    }

    @Override
    protected String getOrganizationName() {
        return "cli";
    }

    @Override
    protected String getRepository() {
        return "cli";
    }
}
