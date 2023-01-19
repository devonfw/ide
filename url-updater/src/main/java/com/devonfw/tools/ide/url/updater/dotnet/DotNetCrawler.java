package com.devonfw.tools.ide.url.updater.dotnet;

import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class DotNetCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "dotnet";
    }


    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        boolean success2 = doUpdateVersion(urlVersion, "https://dotnetcli.azureedge.net/dotnet/Sdk/${version}/dotnet-sdk-${version}-win-x64.exe", OSType.WINDOWS,"x64");
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

    @Override
    protected String getOrganizationName() {
        return "dotnet";
    }
    @Override
    protected String getRepository(){
        return "sdk";
    }
}
