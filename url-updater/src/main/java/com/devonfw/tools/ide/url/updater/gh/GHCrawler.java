package com.devonfw.tools.ide.url.updater.gh;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class GHCrawler extends GithubCrawler {
	@Override
	protected String getToolName() {
		return "gh";
	}

	@Override
	protected String mapVersion(String version) {
		String version2 = version.replaceAll("v", "");
		//filter out pre releases
		if (version2.contains("-")) {
			return version2.substring(0, version2.indexOf("-"));
		}
		return version2;
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_windows_amd64.zip", OSType.WINDOWS, "x64");
		doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_linux_amd64.tar.gz", OSType.LINUX, "x64");
		doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_linux_arm64.tar.gz", OSType.LINUX, "arm64");
		doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_macOS_amd64.tar.gz", OSType.MAC, "x64");
		doUpdateVersion(urlVersion, "https://github.com/cli/cli/releases/download/v${version}/gh_${version}_macOS_arm64.tar.gz", OSType.MAC, "arm64");
	}

	@Override
	protected String getOrganizationName() {
		return "cli";
	}

	@Override
	protected String getRepository() {
		return "cli";
	}
}
