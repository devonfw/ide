package com.devonfw.tools.ide.url.updater.lazydocker;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class LazyDockerCrawler extends GithubCrawler {
	@Override
	protected String getToolName() {
		return "lazydocker";
	}

	@Override
	protected String mapVersion(String version) {
		return version.replace("v", "");
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Windows_x86_64.zip", OSType.WINDOWS, "x64");
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Windows_arm64.zip", OSType.WINDOWS, "arm64");
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Linux_x86_64.tar.gz", OSType.LINUX, "x64");
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Linux_arm64.tar.gz", OSType.LINUX, "arm64");
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Darwin_x86_64.tar.gz", OSType.MAC, "x64");
		doUpdateVersion(urlVersion, "https://github.com/jesseduffield/lazydocker/releases/download/v${version}/lazydocker_${version}_Darwin_arm64.tar.gz", OSType.MAC, "arm64");
	}

	@Override
	protected String getOrganizationName() {
		return "jesseduffield";
	}

	@Override
	protected String getRepository() {
		return "lazydocker";
	}
}
