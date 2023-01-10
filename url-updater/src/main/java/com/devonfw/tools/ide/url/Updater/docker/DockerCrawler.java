//package com.devonfw.tools.ide.url.Updater.docker;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class DockerCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d\\.\\d+\\.\\d+)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Docker";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "Docker";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://docs.docker.com/desktop/release-notes/";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://desktop.docker.com/${os}/main/${arch}/${release}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.oses.put(OSType.WINDOWS, "win");
//        mappings.oses.put(OSType.MAC, "mac");
//        mappings.architectures.put("aarch64", "arm64");
//        mappings.architectures.put("x64", "amd64");
//        mappings.extensions.put(OSType.MAC, "dmg");
//        mappings.extensions.put(OSType.WINDOWS, "exe");
//        mappings.releases.add("Docker%20Desktop%20Installer");
//        mappings.releases.add("Docker");
//        return mappings;
//    }
//}
