package com.devonfw.tools.ide.url.updater.graalvm;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubCrawler;
import com.devonfw.tools.ide.url.updater.OSType;

public class GraalVMCrawler extends GithubCrawler {
	@Override
	protected String getToolName() {
		return "graalvm";
	}

	@Override
	protected String mapVersion(String version) {
		return version.replaceAll("vm-", "");
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-windows-amd64-${version}.zip", OSType.WINDOWS, "x64");
		doUpdateVersion(urlVersion, "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-linux-amd64-${version}.tar.gz", OSType.LINUX, "x64");
		doUpdateVersion(urlVersion, "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-darwin-amd64-${version}.tar.gz", OSType.MAC, "x64");
		doUpdateVersion(urlVersion, "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${version}/graalvm-ce-java11-darwin-aarch64-${version}.tar.gz", OSType.MAC, "arm64");

	}

	@Override
	protected String getOrganizationName() {
		return "graalvm";
	}

	@Override
	protected String getRepository() {
		return "graalvm-ce-builds";
	}
}
