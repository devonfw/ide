package com.devonfw.tools.ide.url.updater.terraform;

import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class TerraformCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "terraform";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://releases.hashicorp.com/terraform/${version}/terraform_${version}_windows_amd64.zip", OSType.WINDOWS);
        doUpdateVersion(urlVersion, "https://releases.hashicorp.com/terraform/${version}/terraform_${version}_linux_amd64.zip", OSType.LINUX);
        doUpdateVersion(urlVersion, "https://releases.hashicorp.com/terraform/${version}/terraform_${version}_darwin_amd64.zip", OSType.MAC);
    }

    @Override
    protected String mapVersion(String version) {
        return version.replace("v", "");
    }

    @Override
    protected String getOrganizationName() {
        return "hashicorp";
    }


}
