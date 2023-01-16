package com.devonfw.tools.ide.url.Updater.az;

import com.devonfw.tools.ide.url.Updater.GithubCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

public class AzureCrawler extends GithubCrawler {
    @Override
    protected String getToolName() {
        return "Azure";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
       doUpdateVersion(urlVersion, "https://azcliprod.blob.core.windows.net/msi/azure-cli-${version}.msi");
    }


    @Override
    protected String getOrganizationName() {
        return "Azure";
    }
    @Override
    protected String getRepository(){
        return "azure-cli";
    }
}
