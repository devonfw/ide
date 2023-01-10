//package com.devonfw.tools.ide.url.Updater.oc;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class OcCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d\\.\\d\\.\\d*)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "oc";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "oc";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-${os}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.extensions.put(OSType.WINDOWS, "zip");
//        mappings.extensions.put(OSType.LINUX, "tar.gz");
//        mappings.extensions.put(OSType.MAC, "tar.gz");
//        mappings.oses.put(OSType.WINDOWS, "windows");
//        mappings.oses.put(OSType.LINUX, "linux");
//        mappings.oses.put(OSType.MAC, "mac");
//        return mappings;
//    }
//}
