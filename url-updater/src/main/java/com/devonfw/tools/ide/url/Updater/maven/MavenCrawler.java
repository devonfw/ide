//package com.devonfw.tools.ide.url.Updater.maven;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class MavenCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d\\.\\d\\.\\d)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Maven";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "Maven";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://archive.apache.org/dist/maven/maven-3/";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://archive.apache.org/dist/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        return new Mappings();
//    }
//}
