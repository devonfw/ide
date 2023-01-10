//package com.devonfw.tools.ide.url.Updater.aws;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.*;
//import java.util.regex.Pattern;
//
//
//public class AWSCrawler extends WebsiteVersionCrawler {
//
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]*");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "aws";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "aws";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/aws/aws-cli/git/refs/tags";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://awscli.amazonaws.com/${os}-${version}.${ext}");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        HashMap<OSType, String> oses = new HashMap<>();
//        oses.put(OSType.WINDOWS, "AWSCLIV2");
//        oses.put(OSType.LINUX, "awscli-exe-linux-x86_64");
//        oses.put(OSType.MAC, "AWSCLIV2");
//        HashMap<String, String> architectures = new HashMap<>();
//        architectures.put("x64", "x64");
//        HashMap<OSType, String> extension = new HashMap<>();
//        extension.put(OSType.WINDOWS, "msi");
//        extension.put(OSType.LINUX, "zip");
//        extension.put(OSType.MAC, "pkg");
//        return new Mappings(oses, architectures, extension);
//    }
//}
//
