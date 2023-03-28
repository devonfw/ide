package com.devonfw.tools.ide.url.updater.helm;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class HelmCrawler extends GithubCrawler {

	@Override
	protected String getToolName() {
		return "helm";
	}

	@Override
	protected String mapVersion(String version) {
		if (version.startsWith("v")) {
			version = version.substring(1);
		}
		return version;
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://get.helm.sh/helm-v${version}-windows-amd64.zip", OSType.WINDOWS);
		doUpdateVersion(urlVersion, "https://get.helm.sh/helm-v${version}-linux-amd64.tar.gz", OSType.LINUX);
		doUpdateVersion(urlVersion, "https://get.helm.sh/helm-v${version}-darwin-amd64.tar.gz", OSType.MAC);
		doUpdateVersion(urlVersion, "https://get.helm.sh/helm-v${version}-darwin-arm64.tar.gz", OSType.MAC, "arm64");
	}

	@Override
	protected String getOrganizationName() {
		return "helm";
	}


}
