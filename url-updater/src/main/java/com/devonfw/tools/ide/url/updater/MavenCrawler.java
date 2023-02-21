package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.updater.mavenapiclasses.Metadata;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MavenCrawler extends AbstractCrawler {
    protected abstract String getArtifcatId();

    protected abstract String getGroupIdPath();

    private final String mavenBaseRepoUrl;

    private final static Logger logger = Logger.getLogger(MavenCrawler.class.getName());

    public MavenCrawler() {
        super();
        this.mavenBaseRepoUrl = "https://repo1.maven.org/maven2/" + getGroupIdPath() + "/" + getArtifcatId() + "/";

    }
    protected String getExtension(){
        return ".jar";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        String version = urlVersion.getName();
        String url = mavenBaseRepoUrl + version + "/" + getArtifcatId() + "-" + version + getExtension();
        doUpdateVersion(urlVersion, url);
    }


    private Set<String> doGetVersionsFromMavenApi(String url) {
        Set<String> versions = new HashSet<>();
        try {
            String response = doGetResponseBody(url);
            XmlMapper mapper = new XmlMapper();
            Metadata metaData = mapper.readValue(response, Metadata.class);
            versions.addAll(metaData.getVersioning().getVersions());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while getting javaJsonVersions", e);
        }
        logger.log(Level.INFO, "Found  javaJsonVersions : " + versions);

        return versions;
    }

    @Override
    protected Set<String> getVersions() {
        return doGetVersionsFromMavenApi(this.mavenBaseRepoUrl + "maven-metadata.xml");
    }
}
