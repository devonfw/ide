package com.devonfw.tools.ide.url.updater.nodejs;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class NodeJsCrawler extends GithubCrawler {
	@Override
	protected String getToolName() {
		return "nodejs";
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-win-x64.zip", OSType.WINDOWS);
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-win-aarch64.zip", OSType.WINDOWS, "arm64");
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-linux-x64.tar.gz", OSType.LINUX);
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-linux-aarch64.tar.gz", OSType.LINUX, "arm64");
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-darwin-x64.tar.gz", OSType.MAC);
		doUpdateVersion(urlVersion, "https://nodejs.org/dist/${version}/node-${version}-darwin-aarch64.tar.gz", OSType.MAC, "arm64");
	}


	@Override
	protected String getOrganizationName() {
		return "nodejs";
	}

	@Override
	protected String getRepository() {
		return "node";
	}
}
