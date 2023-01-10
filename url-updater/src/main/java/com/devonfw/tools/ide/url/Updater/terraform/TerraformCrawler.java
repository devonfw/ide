//package com.devonfw.tools.ide.url.Updater.terraform;
//
//import com.devonfw.tools.ide.url.Updater.Mappings;
//import com.devonfw.tools.ide.url.Updater.OSType;
//import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class TerraformCrawler extends WebsiteVersionCrawler {
//    @Override
//    protected Pattern getVersionPattern() {
//        return Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
//    }
//
//    @Override
//    protected String getToolName() {
//        return "Terraform";
//    }
//
//    @Override
//    protected String getEdition() {
//        return "Terraform";
//    }
//
//    @Override
//    protected String getVersionUrl() {
//        return "https://api.github.com/repos/hashicorp/terraform/git/refs/tags";
//    }
//
//    @Override
//    protected List<String> getDownloadUrls() {
//        ArrayList<String> downloadUrls = new ArrayList<>();
//        downloadUrls.add("https://releases.hashicorp.com/terraform/${version}/terraform_${version}_${os}.zip");
//        return downloadUrls;
//    }
//
//    @Override
//    protected Mappings getMappings() {
//        Mappings mappings = new Mappings();
//        mappings.oses.put(OSType.WINDOWS,"windows_amd64");
//        mappings.oses.put(OSType.LINUX,"linux_amd64");
//        mappings.oses.put(OSType.MAC,"darwin_amd64");
//        return mappings;
//    }
//}
