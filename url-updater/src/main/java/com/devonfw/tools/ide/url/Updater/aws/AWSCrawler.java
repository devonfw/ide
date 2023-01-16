package com.devonfw.tools.ide.url.Updater.aws;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class AWSCrawler extends GithubCrawler {

    @Override
    protected String getToolName() {
        return "aws";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
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
