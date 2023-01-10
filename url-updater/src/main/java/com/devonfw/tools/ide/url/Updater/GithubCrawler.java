package com.devonfw.tools.ide.url.Updater;

import com.devonfw.tools.ide.url.Updater.githubapiclasses.GithubJsonItem;
import com.devonfw.tools.ide.url.Updater.githubapiclasses.GithubJsonObject;


import java.util.Collection;



public abstract class GithubCrawler extends JsonCrawler<GithubJsonObject> {
    @Override
    protected Class<GithubJsonObject> getJsonObjectType() {
        return GithubJsonObject.class;
    }

    @Override
    protected void collectVersionsFromJson(GithubJsonObject jsonItem, Collection<String> versions) {
        for (GithubJsonItem item : jsonItem) {
            String version =item.ref().replace("refs/tags/", "");
            versions.add(version);
        }
    }

    @Override
    protected String doGetVersionUrl() {
        return "https://api.github.com/repos/" + getOrganizationName() + "/" + getRepository() + "/git/refs/tags";

    }

    protected abstract String getOrganizationName();
    protected String getRepository(){
        return getToolName();
    }


}
