//package com.devonfw.tools.ide.url.Updater.gh;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class GHCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
//    }
//    @Override
//    protected String getToolName() {
//        return "gh";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "gh";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/cli/cli/git/refs/tags";
//    }
//
//    @Override
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
//}
