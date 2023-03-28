package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.updater.githubapiclasses.GithubJsonItem;
import com.devonfw.tools.ide.url.updater.githubapiclasses.GithubJsonObject;

import java.util.Collection;


public abstract class GithubCrawler extends JsonCrawler<GithubJsonObject> {
	@Override
	protected String doGetVersionUrl() {
		return "https://api.github.com/repos/" + getOrganizationName() + "/" + getRepository() + "/git/refs/tags";

	}

	@Override
	protected Class<GithubJsonObject> getJsonObjectType() {
		return GithubJsonObject.class;
	}

	@Override
	protected void collectVersionsFromJson(GithubJsonObject jsonItem, Collection<String> versions) {
		for (GithubJsonItem item : jsonItem) {
			String version = item.getRef().replace("refs/tags/", "");
			if (version.contains("alpha") || version.contains("beta") || version.contains("dev") || version.contains("rc") || version.contains("snapshot") || version.contains("preview") || version.equals("")) {
				continue;
			}
			versions.add(version);
		}
	}

	protected abstract String getOrganizationName();

	protected String getRepository() {
		return getToolName();
	}


}
