package com.devonfw.tools.ide.url.updater.rancher;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class RancherCrawler extends GithubCrawler {
	@Override
	protected String getToolName() {
		return "docker";
	}

	@Override
	protected String getEdition() {
		return "rancher";
	}

	@Override
	protected String mapVersion(String version) {
		return version.replace("v", "");
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop.Setup.${version}.exe", OSType.WINDOWS);
		doUpdateVersion(urlVersion, "https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop-${version}.x86_64.dmg", OSType.MAC);
		doUpdateVersion(urlVersion, "https://github.com/rancher-sandbox/rancher-desktop/releases/download/v${version}/Rancher.Desktop-${version}.aarch64.dmg", OSType.MAC, "arm64");

	}

	@Override
	protected String getOrganizationName() {
		return "rancher-sandbox";
	}

	@Override
	protected String getRepository() {
		return "rancher-desktop";
	}
}
