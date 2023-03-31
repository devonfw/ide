package com.devonfw.tools.ide.url.updater.eclipse;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

import java.util.regex.Pattern;

public abstract class EclipseCrawler extends WebsiteCrawler {

	@Override
	protected String getToolName() {
		return "eclipse";
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		//archive
		boolean windowsVersionExists = doUpdateVersion(urlVersion, "https://archive.eclipse.org/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-win32-x86_64.zip", OSType.WINDOWS, "x64", getEdition());
		if (!windowsVersionExists) return;

		doUpdateVersion(urlVersion, "https://archive.eclipse.org/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://archive.eclipse.org/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());
		doUpdateVersion(urlVersion, "https://archive.eclipse.org/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-x86_64.tar.gz", OSType.MAC, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://archive.eclipse.org/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-aarch64.tar.gz", OSType.MAC, "arm64", getEdition());

		//utwente
		doUpdateVersion(urlVersion, "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-win32-x86_64.zip", OSType.WINDOWS, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-aarch64.tar.gz", OSType.MAC, "arm64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-x86_64.tar.gz", OSType.MAC, "x64", getEdition());

		//osuosl
		doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-win32-x86_64.zip", OSType.WINDOWS, "x64", getEdition());
		// doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/M1/eclipse-${edition}-${version}-M1-win32-x86_64.zip", OSType.WINDOWS, "x64", getEdition());

		doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		// doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/M1/eclipse-${edition}-${version}-M1-linux-gtk-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-linux-gtk-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());
		// doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/M1/eclipse-${edition}-${version}-M1-linux-gtk-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());

		doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		// doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/M1/eclipse-${edition}-${version}-M1-macosx-cocoa-x86_64.tar.gz", OSType.LINUX, "x64", getEdition());
		doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/R/eclipse-${edition}-${version}-R-macosx-cocoa-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());
		// doUpdateVersion(urlVersion, "https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/M1/eclipse-${edition}-${version}-M1-macosx-cocoa-aarch64.tar.gz", OSType.LINUX, "arm64", getEdition());
	}

	@Override
	protected String getVersionUrl() {
		return "https://www.eclipse.org/downloads/packages/release";
	}

	@Override
	protected Pattern getVersionPattern() {
		return Pattern.compile("\\d{4}-\\d{2}");
	}

}
