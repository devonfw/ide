//package com.devonfw.tools.ide.url.Updater.dotnet;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class DotNetCrawler extends WebsiteVersionCrawler{
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "dotnet";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "dotnet";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/dotnet/sdk/git/refs/tags";
//    }
//
//    @Override
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
//}