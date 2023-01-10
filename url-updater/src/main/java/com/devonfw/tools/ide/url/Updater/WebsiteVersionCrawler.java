package com.devonfw.tools.ide.url.Updater;

import com.devonfw.tools.ide.url.Updater.githubapiclasses.*;
import com.devonfw.tools.ide.url.Updater.githubapiclasses.GithubJsonObject;
import com.devonfw.tools.ide.url.Updater.mavenapiclasses.Metadata;
import com.devonfw.tools.ide.url.folderhandling.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.asynchttpclient.uri.Uri;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class WebsiteVersionCrawler extends AbstractCrawler {
    protected abstract Pattern getVersionPattern();


    protected abstract String getVersionUrl();

    private final Logger logger = Logger.getLogger(WebsiteVersionCrawler.class.getName());

    protected abstract List<String> getDownloadUrls();

    protected abstract Mappings getMappings();


    protected List<String> doGetRegexMatchesAsList(String input) {
        List<String> versions = new ArrayList<>();
        var matcher = getVersionPattern().matcher(input);
        while (matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            String match = result.group();
            if (!versions.contains(match)) {
                versions.add(match);
            }
        }
        logger.log(Level.INFO, "Found  javaJsonVersions : " + versions);
        return versions;
    }

    private List<String> doGetVersionsFromMavenApi(String url) {
        List<String> versions = new ArrayList<>();
        try {
            String response = doGetResponseBody(url);
            XmlMapper mapper = new XmlMapper();
            Metadata metaData = mapper.readValue(response, Metadata.class);
            for (String version : metaData.versioning().versions()) {
                if (!versions.contains(version)) {
                    versions.add(version);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while getting javaJsonVersions", e);
        }
        logger.log(Level.INFO, "Found  javaJsonVersions : " + versions);

        return versions;
    }

    private List<String> doGetVersionsFromGitHubApi(String url) {
        List<String> versions = new ArrayList<>();
        try {
            String response = doGetResponseBody(url);
            ObjectMapper mapper = new ObjectMapper();
            GithubJsonObject githubJsonObject = mapper.readValue(response, GithubJsonObject.class);
            for (GithubJsonItem item : githubJsonObject) {
                Matcher matcher = getVersionPattern().matcher(item.ref());
                while (matcher.find()) {
                    MatchResult result = matcher.toMatchResult();
                    String match = result.group();
                    if (!versions.contains(match)) {
                        versions.add(match);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while getting javaJsonVersions from github api", e);
        }
        logger.log(Level.INFO, "Found  javaJsonVersions : " + versions);

        return versions;
    }


    protected List<String> doGetVersions(String url) {
        if (isGitHubUrl(url)) {
            return doGetVersionsFromGitHubApi(url);
        }
        if (isMavenUrl(url)) {
            return doGetVersionsFromMavenApi(url);
        }
        return doGetRegexMatchesAsList(doGetResponseBody(url));
    }

    private boolean isGitHubUrl(String url) {
        return Objects.equals(URI.create(url).getHost(), "api.github.com");
    }

    private boolean isMavenUrl(String url) {
        return Uri.create(url).getHost().contains("maven.org");
    }


    public void doUpdate(UrlRepository urlRepository) {
        UrlTool tool = urlRepository.getOrCreateChild(getToolName());
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        List<String> versions = doGetVersions(getVersionUrl());
        List<String> existingVersions = edition.getListOfAllChildren();
        //get the javaJsonVersions that are not in the existing javaJsonVersions
        versions.removeAll(existingVersions);
        for (String version : versions) {
            Map<String, Set<String>> fileNameWithUrls = doGetWorkingDownloadUrlsForGivenVersion(version);
            UrlVersion urlVersion = edition.getOrCreateChild(version);
            for (Map.Entry<String, Set<String>> entry : fileNameWithUrls.entrySet()) {
                String fileName = entry.getKey();
                Set<String> urls = entry.getValue();
                //TODO:Check dass nur Versionen bzw urls erzeugt werden die es auch gibt
                UrlDownloadFile urlFile = (UrlDownloadFile) urlVersion.getOrCreateChild(fileName);
                for (String url : urls) {
                    urlFile.addUrl(url);
                }
            }
            urlVersion.save();


        }
    }

    protected void doCreatePathAndFileIfNonexistent(Path pathToFile) {
    	File f = new File(pathToFile.toString());
        File parentFolder = f.getParentFile();
        if ( !parentFolder.exists() ) {
            parentFolder.mkdirs();
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException("The file " + f.getPath() + "doesn't exist." ,e);
            }


        }
    }


    protected Map<String, Set<String>> doGetWorkingDownloadUrlsForGivenVersion(String version) {
        Map<String, Set<String>> urlsByOsAndArch = new HashMap<>();

        // Create a regular expression to match placeholders in the URL
        Pattern placeholderPattern = Pattern.compile("\\$\\{(\\w+)\\}");

        // Iterate over all URLs
        for (String url : getDownloadUrls()) {
            // Iterate over all possible combinations of the parameters
            for (String r : getMappings().releases) {
                getMappings().oses.forEach((oskey, o) -> {
                    getMappings().architectures.forEach((archkey, arch) -> {
                        // Substitute the parameters in the URL
                        String combination = url;
                        Matcher matcher = placeholderPattern.matcher(combination);
                        StringBuilder sb = new StringBuilder();
                        int i = 0;
                        while (matcher.find()) {
                            String placeholder = matcher.group(1);
                            String replacement = switch (placeholder) {
                                case "version" -> version;
                                case "release" -> r;
                                case "edition" -> getEdition();
                                case "os" -> o;
                                case "arch" -> arch;
                                case "ext" -> getMappings().extensions.get(oskey);
                                default -> null;
                            };
                            sb.append(combination, i, matcher.start());
                            if (replacement != null) {
                                sb.append(replacement);
                            }
                            i = matcher.end();
                        }
                        sb.append(combination.substring(i));
                        combination = sb.toString();
                        if (doCheckIfDownloadUrlWorks(combination).isSuccess()) {
                            logger.info("Found working URL for ARCH " + o + arch + " " + combination);
                            String fileName = oskey + "_" + archkey + ".urls";
                            Set<String> urlsForOsAndArch = doGetOrCreateUrlsForOsAndArch(urlsByOsAndArch, fileName);
                            urlsForOsAndArch.add(combination);
                        }
                    });
                });
            }
        }
        return urlsByOsAndArch;
    }

    protected Set<String> doGetOrCreateUrlsForOsAndArch(Map<String, Set<String>> urlsByOsAndArch, String fileName) {
        return urlsByOsAndArch.computeIfAbsent(fileName, k -> new HashSet<>());
    }

}
