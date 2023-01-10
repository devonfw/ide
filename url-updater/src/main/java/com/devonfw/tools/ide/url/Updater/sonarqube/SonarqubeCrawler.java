//package com.devonfw.tools.ide.url.Updater.sonarqube;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class SonarqubeCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d+\\.\\d+[\\.\\d+]*)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Sonarqube";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "Sonarqube";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/SonarSource/sonarqube/git/refs/tags";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-${version}.zip");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        return new Mappings();
//    }
//}
