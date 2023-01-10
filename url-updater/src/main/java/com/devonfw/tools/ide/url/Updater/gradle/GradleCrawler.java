package com.devonfw.tools.ide.url.Updater.gradle;

import com.devonfw.tools.ide.url.Updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class GradleCrawler extends WebsiteCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d[\\.\\d]*)");
    }

    @Override
    protected String getToolName() {
        return "Gradle";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://services.gradle.org/distributions/gradle-${version}-bin.zip");

    }
    @Override
    protected String getVersionUrl() {
        return "https://gradle.org/releases/";
    }


//    @Override
//    protected Map<String, Set<String>> doGetWorkingDownloadUrlsForGivenVersion(String version) {
//        Map<String, Set<String>> urlsByOsAndArch = new HashMap<>();
//
//        // Create a regular expression to match placeholders in the URL
//        Pattern placeholderPattern = Pattern.compile("\\$\\{(\\w+)\\}");
//        // Iterate over all URLs
//        for (String url : getDownloadUrls()) {
//            // Substitute the parameters in the URL
//            String combination = url;
//            Matcher matcher = placeholderPattern.matcher(combination);
//            StringBuilder sb = new StringBuilder();
//            int i = 0;
//            while (matcher.find()) {
//                String placeholder = matcher.group(1);
//                String replacement = switch (placeholder) {
//                    case "version" -> version;
//                    default -> null;
//                };
//                sb.append(combination, i, matcher.start());
//                if (replacement != null) {
//                    sb.append(replacement);
//                }
//                i = matcher.end();
//            }
//            sb.append(combination.substring(i));
//            combination = sb.toString();
//            if (doCheckIfDownloadUrlWorks(combination).isSuccess()) {
//                String fileName = "urls";
//                Set<String> urlsForOsAndArch = doGetOrCreateUrlsForOsAndArch(urlsByOsAndArch, fileName);
//                urlsForOsAndArch.add(combination);
//            }
//        }
//        return urlsByOsAndArch;
//
//    }
}
