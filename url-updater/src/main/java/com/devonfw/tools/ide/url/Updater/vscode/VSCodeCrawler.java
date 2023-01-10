//package com.devonfw.tools.ide.url.Updater.vscode;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class VSCodeCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("refs\\/tags\\/(\\d+\\.\\d+\\.\\d+)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "vscode";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "vscode";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/microsoft/vscode/git/refs/tags";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://update.code.visualstudio.com/${version}/${os}/stable");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        HashMap<OSType, String> oses = new HashMap<>();
//        oses.put(OSType.WINDOWS, "win32-x64-archive");
//        oses.put(OSType.LINUX, "linux-x64");
//        oses.put(OSType.MAC, "darwin");
//        return new Mappings(oses);
//    }
//}
