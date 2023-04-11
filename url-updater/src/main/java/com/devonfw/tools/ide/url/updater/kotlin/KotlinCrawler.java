package com.devonfw.tools.ide.url.updater.kotlin;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

import java.util.regex.Pattern;

public class KotlinCrawler extends WebsiteCrawler {
	@Override
	protected String getToolName() {
		return "kotlin";
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		doUpdateVersion(urlVersion, "https://github.com/JetBrains/kotlin/releases/download/v${version}/kotlin-compiler-${version}.zip");
	}

	@Override
	protected String getVersionUrl() {
		return "https://api.github.com/repos/JetBrains/kotlin/releases";
	}

	@Override
	protected Pattern getVersionPattern() {
		return Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+");
	}
}
