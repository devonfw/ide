package com.devonfw.tools.ide.url.updater.aws;

import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class AWSCrawler extends GithubCrawler {

    @Override
    protected String getToolName() {
        return "aws";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        if(!urlVersion.getName().startsWith("2")) return; // There are no valid download links for aws-cli below version 2
        boolean success = doUpdateVersion(urlVersion, "https://awscli.amazonaws.com/AWSCLIV2-${version}.msi", OSType.WINDOWS);
        if(!success){
            return;
        }
        doUpdateVersion(urlVersion, "https://awscli.amazonaws.com/awscli-exe-linux-x86_64-${version}.zip", OSType.LINUX);
        doUpdateVersion(urlVersion, "https://awscli.amazonaws.com/AWSCLIV2-${version}.pkg", OSType.MAC);
    }

    @Override
    protected String getOrganizationName() {
        return "aws";
    }
    @Override
    protected String getRepository(){
        return "aws-cli";
    }
}
