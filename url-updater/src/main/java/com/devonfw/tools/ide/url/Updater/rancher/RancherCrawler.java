//package com.devonfw.tools.ide.url.Updater.rancher;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class RancherCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Docker";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "rancher";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/rancher-sandbox/rancher-desktop/git/refs/tags";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop.Setup.${version}.${ext}");
//        downloadUrls.add("https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop-${version}.${arch}.${ext}");
//        downloadUrls.add("https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/rancher-desktop-linux-v${version}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.architectures.put("x86_64", "x86_64");
//        mappings.architectures.put("arm64", "aarch64");
//        mappings.extensions.put(OSType.WINDOWS, "exe");
//        mappings.extensions.put(OSType.MAC, "dmg");
//        mappings.extensions.put(OSType.LINUX, "zip");
//        return mappings;
//    }
//}
