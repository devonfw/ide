package com.devonfw.tools.ide.url.Updater.helm;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class HelmCrawler extends GithubCrawler {

    @Override
    protected String getToolName() {
        return "helm";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion,"https://get.helm.sh/helm-${version}-windows-amd64.zip", OSType.WINDOWS);
        doUpdateVersion(urlVersion,"https://get.helm.sh/helm-${version}-linux-amd64.tar.gz", OSType.LINUX);
        doUpdateVersion(urlVersion,"https://get.helm.sh/helm-${version}-darwin-amd64.tar.gz", OSType.MAC);
    }

    @Override
    protected String getOrganizationName() {
        return "helm";
    }

    @Override
    protected String mapVersion(String version) {
        if(version.startsWith("v")){
         version = version.substring(1);
        }
        return version;
    }

//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://get.helm.sh/helm-${version}-${os}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.extensions.put(OSType.WINDOWS, "zip");
//        mappings.extensions.put(OSType.LINUX, "tar.gz");
//        mappings.extensions.put(OSType.MAC, "tar.gz");
//        mappings.oses.put(OSType.WINDOWS, "windows-amd64");
//        mappings.oses.put(OSType.LINUX, "linux-amd64");
//        mappings.oses.put(OSType.MAC, "darwin-amd64");
//        return mappings;
//    }
}
