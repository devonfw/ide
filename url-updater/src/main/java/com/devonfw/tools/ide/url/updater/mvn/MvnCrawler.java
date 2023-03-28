package com.devonfw.tools.ide.url.updater.mvn;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

import java.util.regex.Pattern;

public class MvnCrawler extends WebsiteCrawler {
	@Override
	protected String getToolName() {
		return "mvn";
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://archive.apache.org/dist/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz");
	}

	@Override
	protected String getVersionUrl() {
		return "https://archive.apache.org/dist/maven/maven-3/";
	}

	@Override
	protected Pattern getVersionPattern() {
		return Pattern.compile("(\\d\\.\\d\\.\\d)");
	}
}
