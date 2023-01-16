package com.devonfw.tools.ide.url.Updater.dotnet;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class DotNetCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "dotnet";
    }


    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        boolean success2 =doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-win-x64.exe", OSType.WINDOWS,"x64");
        boolean success  = doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-win-arm64.exe", OSType.WINDOWS,"arm64");
        if(!success && !success2){
            return;
        }
        doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-linux-x64.tar.gz", OSType.LINUX,"x64");
        doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-linux-arm64.tar.gz", OSType.LINUX,"arm64");
        doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-osx-x64.tar.gz", OSType.MAC,"x64");
        doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-osx-arm64.tar.gz", OSType.MAC,"arm64");
    }

    @Override
    protected String mapVersion(String version) {
        return version.replace("v","");
    }
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-${os}-${arch}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.oses.put(OSType.WINDOWS,"win");
//        mappings.oses.put(OSType.LINUX,"linux");
//        mappings.oses.put(OSType.MAC,"osx");
//        mappings.architectures.put("x86_64","x64");
//        mappings.architectures.put("arm64","arm64");
//        mappings.extensions.put(OSType.WINDOWS,"zip");
//        mappings.extensions.put(OSType.LINUX,"tar.gz");
//        mappings.extensions.put(OSType.MAC,"tar.gz");
//        return mappings;
//    }
    @Override
    protected String getOrganizationName() {
        return "dotnet";
    }
    @Override
    protected String getRepository(){
        return "sdk";
    }
}
