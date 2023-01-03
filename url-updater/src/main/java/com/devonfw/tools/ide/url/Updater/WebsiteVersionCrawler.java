package com.devonfw.tools.ide.url.Updater;

import com.devonfw.tools.ide.url.Updater.githubapiclasses.GithubJsonItem;
import com.devonfw.tools.ide.url.Updater.githubapiclasses.GithubJsonObject;
import com.devonfw.tools.ide.url.Updater.mavenapiclasses.Metadata;
import com.devonfw.tools.ide.url.folderhandling.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.apache.logging.log4j.core.jackson.Log4jXmlObjectMapper;
import org.asynchttpclient.uri.Uri;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class WebsiteVersionCrawler extends AbstractCrawler {
    protected abstract Pattern getVersionPattern();

    protected abstract String getToolName();

    protected abstract String getEdition();

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
        logger.log(Level.INFO, "Found  versions : " + versions);
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
            logger.log(Level.SEVERE, "Error while getting versions", e);
        }
        logger.log(Level.INFO, "Found  versions : " + versions);

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
            logger.log(Level.SEVERE, "Error while getting versions from github api", e);
        }
        logger.log(Level.INFO, "Found  versions : " + versions);

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
    protected Result<Integer> doCheckIfDownloadUrlWorks(String downloadUrl) {
        // Do Head request to check if the download url works and if the file is available for download
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Return the success or failure result
            return response.statusCode() >= 200 && response.statusCode() < 400
                    ? new Result<>(true, response.statusCode())
                    : new Result<>(false, response.statusCode());
        } catch (IOException | InterruptedException e) {
            return new Result<>(false, -404);
        }
    }

    public void doUpdate(UrlRepository urlRepository) {
        UrlTool tool = urlRepository.getOrCreateChild(getToolName());
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        List<String> versions = doGetVersions(getVersionUrl());
        List<String> existingVersions = edition.getChildrenInDirectory();
        //get the versions that are not in the existing versions
        versions.removeAll(existingVersions);
        for (String version : versions) {
            Map<String, Set<String>> fileNameWithUrls = doGetWorkingDownloadUrlsForGivenVersion(version);
            UrlVersion urlVersion = edition.getOrCreateChild(version);
            for (Map.Entry<String, Set<String>> entry : fileNameWithUrls.entrySet()) {
                String fileName = entry.getKey();
                Set<String> urls = entry.getValue();
                UrlFile urlFile = urlVersion.getOrCreateChild(fileName);
                try {
                    urlFile.addToObjectsList(urls);
                    urlFile.saveListFromObjectIntoFile();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to save url into file", e);
                    //TODO: Christian - add a way to handle this exception
                }
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
                            String fileName = oskey + "_" + archkey;
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
