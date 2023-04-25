package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.updater.mavenapiclasses.Metadata;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The MvnCrawler class is an abstract class that provides functionality for crawling Maven repositories.
 */
public abstract class MavenBasedCrawler extends AbstractCrawler {
	private final static Logger logger = LoggerFactory.getLogger(MavenBasedCrawler.class.getName());
	private final String mavenBaseRepoUrl;

	public MavenBasedCrawler() {
		super();
		this.mavenBaseRepoUrl = "https://repo1.maven.org/maven2/" + getGroupIdPath() + "/" + getArtifcatId() + "/";

	}

	protected abstract String getGroupIdPath();

	protected abstract String getArtifcatId();

	@Override
	protected Set<String> getVersions() {
		return doGetVersionsFromMavenApi(this.mavenBaseRepoUrl + "maven-metadata.xml");
	}

	@Override
	protected void updateVersion(UrlVersion urlVersion) {
		String version = urlVersion.getName();
		String url = mavenBaseRepoUrl + version + "/" + getArtifcatId() + "-" + version + getExtension();
		doUpdateVersion(urlVersion, url);
	}

	protected String getExtension() {
		return ".jar";
	}

	/**
	 * Gets the versions from the Maven API.
	 *
	 * @param url The Url of the metadata.xml file
	 * @return The versions.
	 */
	private Set<String> doGetVersionsFromMavenApi(String url) {
		Set<String> versions = new HashSet<>();
		try {
			String response = doGetResponseBody(url);
			XmlMapper mapper = new XmlMapper();
			Metadata metaData = mapper.readValue(response, Metadata.class);
			versions.addAll(metaData.getVersioning().getVersions());
		} catch (IOException e) {
			logger.error("URLStatusError while getting javaJsonVersions", e);
		}
		logger.info("Found  javaJsonVersions : {}", versions);
		return versions;
	}
}
