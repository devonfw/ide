//package com.devonfw.tools.ide.url.Updater.jenkins;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class JenkinsCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d\\.\\d{2,3}\\.\\d)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Jenkins";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "Jenkins";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://mirrors.jenkins.io/war-stable/";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("http://mirrors.jenkins.io/war-stable/${version}/jenkins.war");
//        downloadUrls.add("http://ftp-nyc.osuosl.org/pub/jenkins/war-stable/${version}/jenkins.war");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        return new Mappings();
//    }
//}
