package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.folderhandling.*;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.Error;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.StatusJson;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.Success;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.URLStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

/**
 * An abstract class representing a web crawler that implements the Updater interface.
 * Contains methods for retrieving response bodies from URLs, updating tool versions, and checking if download URLs work.
 */
public abstract class AbstractCrawler implements Updater {
    protected final HttpClient client = HttpClient.newBuilder().build();

    private static final Logger logger = LoggerFactory.getLogger(AbstractCrawler.class);

    /**
     * Retrieves the response body from a given URL.
     *
     * @param url the URL to retrieve the response body from.
     * @return a string representing the response body.
     * @throws IllegalStateException if the response body could not be retrieved.
     */
    protected String doGetResponseBody(String url) {
        try {
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            return client.send(request1, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
        } catch (IllegalArgumentException e) {
            logger.error("Error while getting response body from url {}", url, e);
            return "";
        }
    }


    /**
     * Creates or refreshes the status JSON file for a given UrlVersion instance based on the result of checking if a download URL works.
     *
     * @param result     the Result instance indicating whether the download URL works.
     * @param urlVersion the UrlVersion instance to create or refresh the status JSON file for.
     */
    private void doCreateOrRefreshStatusJson(Result result, UrlVersion urlVersion) {
        UrlStatusFile urlStatusFile = urlVersion.getOrCreateStatus();

        StatusJson statusJson = urlStatusFile.getJsonFileData();
        List<String> urlFileNames = urlVersion.getListOfAllChildren();
        urlFileNames.remove(UrlStatusFile.STATUS_JSON);
        Set<Double> urlHashes = new HashSet<>();
        for (String urlFileName : urlFileNames) {
            UrlDownloadFile urlDownloadFile = urlVersion.getUrlFile(urlFileName);
            if (urlDownloadFile != null) {
                urlHashes.addAll(urlDownloadFile.generateUrlHashes());
            }
        }
        Map<String, URLStatus> urlStatuses = statusJson.getUrlStatuses();
        if (result.isSuccess()) {
            for (Double urlHash : urlHashes) {
                URLStatus urlStatus = new URLStatus();
                urlStatus.setSuccess(new Success());
                urlStatuses.put(urlHash.toString(), urlStatus);
            }
        }
        if (result.isFailure()) {
            for (Double urlHash : urlHashes) {
                URLStatus urlStatus = new URLStatus();
                if (urlStatus.getError() != null) {
                    return;
                }
                String message = result.getHttpStatusCode() + " " + result.getUrl();
                urlStatus.setError(new Error(message));
                urlStatuses.put(urlHash.toString(), urlStatus);
            }
        }
        statusJson.setUrlStatuses(urlStatuses);
        urlStatusFile.setJsonFileData(statusJson);
    }

    /**
     * Updates a tool version with the given URL, OS type, architecture, and edition.
     *
     * @param urlVersion  the UrlVersion instance to update.
     * @param downloadUrl the URL of the download for the tool.
     * @param os          the operating system type for the tool (can be null).
     * @param arch        the architecture of the operating system (can be null).
     * @param edition     the edition of the tool (can be null).
     * @return true if the version was successfully updated, false otherwise.
     */
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os, String arch, String edition) {
        String version = urlVersion.getName();
        String osString = null;
        downloadUrl = downloadUrl.replace("${version}", version);
        if (os != null) {
            osString = os.toString();
            downloadUrl = downloadUrl.replace("${os}", osString);
        }
        if (arch != null) {
            downloadUrl = downloadUrl.replace("${arch}", arch);
        }
        if (edition != null) {
            downloadUrl = downloadUrl.replace("${edition}", edition);
        }
        Result resultOfHttpRequest = doCheckIfDownloadUrlWorks(downloadUrl);
        if (resultOfHttpRequest.isSuccess()) {
            UrlDownloadFile urlDownloadFile = urlVersion.getOrCreateUrls(osString, arch);
            urlDownloadFile.addUrl(downloadUrl);
            doCreateOrRefreshStatusJson(resultOfHttpRequest, urlVersion);
            urlVersion.save();
            return true;
        } else {
            //check if folder of urlVersion exists
            Path folderPath = Paths.get(urlVersion.getPath().toString());
            if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
                doCreateOrRefreshStatusJson(resultOfHttpRequest, urlVersion);
                urlVersion.save();
            }
            return false;
        }
    }

    /**
     * Overloaded method for updating a tool version with the given URL, OS type, and architecture.
     *
     * @param urlVersion  the UrlVersion instance to update.
     * @param downloadUrl the URL of the download for the tool.
     * @param os          the operating system type for the tool (can be null).
     * @param arch        the architecture of the operating system (can be null).
     * @return true if the version was successfully updated, false otherwise.
     */
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os, String arch) {
        return doUpdateVersion(urlVersion, downloadUrl, os, arch, null);
    }

    /**
     * Overloaded method for updating a tool version with the given URL and operating system type.
     *
     * @param urlVersion  the UrlVersion instance to update.
     * @param downloadUrl the URL of the download for the tool.
     * @param os          the operating system type for the tool (can be null).
     * @return true if the version was successfully updated, false otherwise.
     */
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os) {
        return doUpdateVersion(urlVersion, downloadUrl, os, null);
    }


    /**
     * Operating system independent version of doUpdateVersion.
     *
     * @param urlVersion  the UrlVersion instance to update.
     * @param downloadUrl the URL of the download for the tool.
     * @return true if the version was successfully updated, false otherwise.
     */
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl) {
        return doUpdateVersion(urlVersion, downloadUrl, null);
    }

    /**
     * Checks if a download URL works and if the file is available for download.
     *
     * @param downloadUrl the URL to check.
     * @return a Result object representing the success or failure of the URL check.
     */
    protected Result doCheckIfDownloadUrlWorks(String downloadUrl) {
        // Do Head request to check if the download url works and if the file is available for download
        Result result;
        Level logLevel = Level.INFO;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Return the success or failure result
            result = response.statusCode() >= 200 && response.statusCode() < 400
                    ? new Result(true, response.statusCode(), downloadUrl)
                    : new Result(false, response.statusCode(), downloadUrl);
        } catch (IOException | InterruptedException e) {
            result = new Result(false, 500, downloadUrl);
        }

        if (result.isFailure()) {
            logLevel = Level.WARN;
        }

        logger.atLevel(logLevel).log("Download url: {} is {} with status code: {}", downloadUrl, result.isSuccess() ? "available" : "not available", result.getHttpStatusCode());
        return result;

    }

    protected abstract String getToolName();

    protected String getEdition() {
        return getToolName();
    }

    /**
     * Updates the version of a given URL version.
     *
     * @param urlVersion the URL version to be updated
     */
    protected abstract void updateVersion(UrlVersion urlVersion);

    /**
     * @param version original version.
     * Returns the transformed version
     */
    protected String mapVersion(String version) {
        return version;
    }

    protected abstract Set<String> getVersions();


    /**
     * Updates the tool's versions in the URL repository.
     *
     * @param urlRepository the URL repository to update
     */
    @Override
    public void update(UrlRepository urlRepository) {
        UrlTool tool = urlRepository.getOrCreateChild(getToolName());
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        updateExistingVersions(edition);
        Set<String> versions = getVersions();
        for (String version : versions) {
            version = mapVersion(version);
            if (version != null && edition.getChild(version) == null && !version.isEmpty()) {
                UrlVersion urlVersion = edition.getOrCreateChild(version);
                updateVersion(urlVersion);
                if (urlVersion.getChildCount() != 0) {
                    urlVersion.save();
                }
            }
        }
    }
    /**
     * Updates existing versions of the tool in the URL repository.
     *
     * @param edition the URL edition to update
     */
    protected void updateExistingVersions(UrlEdition edition) {
        List<String> existingVersions = edition.getListOfAllChildren();
        edition.getListOfAllChildren().stream().filter(existingVersions::contains).forEach(version -> {
            UrlVersion urlVersion = edition.getChild(version);
            if (urlVersion != null) {
                UrlStatusFile urlStatusFile = urlVersion.getOrCreateStatus();
                logger.info("Getting or creating Status for version {}", version);
                if (urlStatusFile.getJsonFileData().isManual()) {
                    logger.atInfo().log("Version {} is manual, skipping update", version);
                    return;
                }
                updateVersion(urlVersion);
                urlVersion.save();
            }
        });
    }

}
