//package com.devonfw.tools.ide.url.Updater.pip;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class PipCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d\\.\\d)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "pip";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "pip";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://bootstrap.pypa.io/pip/";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://bootstrap.pypa.io/pip/get-pip.py");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        return new Mappings();
//    }
//}
