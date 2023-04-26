package com.devonfw.tools.ide.url.updater;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.common.SystemArchitecture;
import com.devonfw.tools.ide.url.model.file.UrlChecksum;
import com.devonfw.tools.ide.url.model.file.UrlDownloadFile;
import com.devonfw.tools.ide.url.model.file.UrlStatusFile;
import com.devonfw.tools.ide.url.model.file.json.UrlStatus;
import com.devonfw.tools.ide.url.model.file.json.UrlStatusState;
import com.devonfw.tools.ide.url.model.folder.UrlEdition;
import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.devonfw.tools.ide.url.model.folder.UrlTool;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.util.HexUtil;

/**
 * Abstract base implementation of {@link UrlUpdater}. Contains methods for retrieving response bodies from URLs,
 * updating tool versions, and checking if download URLs work.
 */
public abstract class AbstractUrlUpdater implements UrlUpdater {

  /** {@link OperatingSystem#WINDOWS}. */
  protected static final OperatingSystem WINDOWS = OperatingSystem.WINDOWS;

  /** {@link OperatingSystem#MAC}. */
  protected static final OperatingSystem MAC = OperatingSystem.MAC;

  /** {@link OperatingSystem#LINUX}. */
  protected static final OperatingSystem LINUX = OperatingSystem.LINUX;

  /** {@link SystemArchitecture#X64}. */
  protected static final SystemArchitecture X64 = SystemArchitecture.X64;

  /** {@link SystemArchitecture#ARM64}. */
  protected static final SystemArchitecture ARM64 = SystemArchitecture.ARM64;

  /** The {@link HttpClient} for HTTP requests. */
  protected final HttpClient client = HttpClient.newBuilder().build();

  private static final Logger logger = LoggerFactory.getLogger(AbstractUrlUpdater.class);

  /**
   * @return the name of the {@link UrlTool tool} handled by this updater.
   */
  protected abstract String getTool();

  /**
   * @return the name of the {@link UrlEdition edition} handled by this updater.
   */
  protected String getEdition() {

    return getTool();
  }

  /**
   * @return the combination of {@link #getTool() tool} and {@link #getEdition() edition} but simplified if both are
   *         equal.
   */
  protected final String getToolWithEdition() {

    String tool = getTool();
    String edition = getEdition();
    if (tool.equals(edition)) {
      return tool;
    }
    return tool + "/" + edition;
  }

  /**
   * Retrieves the response body from a given URL.
   *
   * @param url the URL to retrieve the response body from.
   * @return a string representing the response body.
   * @throws IllegalStateException if the response body could not be retrieved.
   */
  protected String doGetResponseBodyAsString(String url) {

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
      return this.client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to retrieve response body from url: " + url, e);
    }
  }

  /**
   * @param url the URL of the download file.
   * @return the {@link InputStream} of response body.
   */
  protected InputStream doGetResponseBodyAsStream(String url) {

    try {
      HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
      return this.client.send(request1, HttpResponse.BodyHandlers.ofInputStream()).body();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to retrieve response body from url: " + url, e);
    }
  }

  /**
   * Updates a tool version with the given arguments (OS independent).
   *
   * @param urlVersion the UrlVersion instance to update.
   * @param downloadUrl the URL of the download for the tool.
   * @return true if the version was successfully updated, false otherwise.
   */
  protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl) {

    return doUpdateVersion(urlVersion, downloadUrl, null);
  }

  /**
   * Updates a tool version with the given arguments.
   *
   * @param urlVersion the UrlVersion instance to update.
   * @param downloadUrl the URL of the download for the tool.
   * @param os the operating system type for the tool (can be null).
   * @return true if the version was successfully updated, false otherwise.
   */
  protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OperatingSystem os) {

    return doUpdateVersion(urlVersion, downloadUrl, os, null);
  }

  /**
   * Updates a tool version with the given arguments.
   *
   * @param urlVersion the UrlVersion instance to update.
   * @param downloadUrl the URL of the download for the tool.
   * @param os the optional {@link OperatingSystem}.
   * @param architecture the optional {@link SystemArchitecture}.
   * @return {@code true} if the version was successfully updated, {@code false} otherwise.
   */
  protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OperatingSystem os,
      SystemArchitecture architecture) {

    String version = urlVersion.getName();
    downloadUrl = downloadUrl.replace("${version}", version);
    if (os != null) {
      downloadUrl = downloadUrl.replace("${os}", os.toString());
    }
    if (architecture != null) {
      downloadUrl = downloadUrl.replace("${arch}", architecture.toString());
    }
    downloadUrl = downloadUrl.replace("${edition}", getEdition());
    UrlRequestResult result = doCheckIfDownloadUrlWorks(downloadUrl);
    if (result.isSuccess()) {
      UrlDownloadFile urlDownloadFile = urlVersion.getOrCreateUrls(os, architecture);
      urlDownloadFile.addUrl(downloadUrl);
      doCreateOrRefreshStatusJson(result, urlVersion, downloadUrl);
      urlVersion.save();

      // generate checksum of download file
      if (result.getHttpStatusCode() == 200) {
        UrlChecksum urlChecksum = urlVersion.getOrCreateChecksum(urlDownloadFile.getName());
        doGenerateChecksum(downloadUrl, urlChecksum);
      }
      return true;
    } else {
      // check if folder of urlVersion exists
      Path folderPath = Paths.get(urlVersion.getPath().toString());
      if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
        doCreateOrRefreshStatusJson(result, urlVersion, downloadUrl);
        urlVersion.save();
      }
      return false;
    }
  }

  /**
   * @param inputStream the input stream of requested url
   * @return checksum of input stream as string
   */
  private String doGenerateChecksumFromUrl(String url) {

    try (InputStream inputStream = doGetResponseBodyAsStream(url)) {
      MessageDigest md = MessageDigest.getInstance(UrlChecksum.HASH_ALGORITHM);

      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        md.update(buffer, 0, bytesRead);
      }
      byte[] digestBytes = md.digest();
      String checksum = HexUtil.toHexString(digestBytes);
      return checksum;
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read body of download " + url, e);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("No such hash algorithm " + UrlChecksum.HASH_ALGORITHM, e);
    }
  }

  /**
   * @param url the url of the download file.
   * @param urlChecksum the {@link UrlChecksum} file.
   */
  public void doGenerateChecksum(String url, UrlChecksum urlChecksum) {

    String checksum = doGenerateChecksumFromUrl(url);
    urlChecksum.setChecksum(checksum);
  }

  /**
   * Checks if a download URL works and if the file is available for download.
   *
   * @param downloadUrl the URL to check.
   * @return a URLRequestResult object representing the success or failure of the URL check.
   */
  protected UrlRequestResult doCheckIfDownloadUrlWorks(String downloadUrl) {

    // Do Head request to check if the download url works and if the file is available for download
    UrlRequestResult result;
    Level logLevel = Level.INFO;
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(downloadUrl))
          .method("HEAD", HttpRequest.BodyPublishers.noBody()).timeout(Duration.ofSeconds(5)).build();

      HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
      // Return the success or failure URLRequestResult
      result = response.statusCode() >= 200 && response.statusCode() < 400
          ? new UrlRequestResult(true, response.statusCode(), downloadUrl)
          : new UrlRequestResult(false, response.statusCode(), downloadUrl);
    } catch (Exception e) {
      result = new UrlRequestResult(false, 500, downloadUrl);
    }

    if (result.isFailure()) {
      logLevel = Level.WARN;
    }

    logger.atLevel(logLevel).log("Download url: {} is {} with status code: {}", downloadUrl,
        result.isSuccess() ? "available" : "not available", result.getHttpStatusCode());
    return result;

  }

  /**
   * Creates or refreshes the status JSON file for a given UrlVersion instance based on the URLRequestResult of checking
   * if a download URL works.
   *
   * @param result the {@link UrlRequestResult} instance indicating whether the download URL works.
   * @param urlVersion the UrlVersion instance to create or refresh the status JSON file for.
   */
  private void doCreateOrRefreshStatusJson(UrlRequestResult result, UrlVersion urlVersion, String downloadUrl) {

    UrlStatusFile urlStatusFile = urlVersion.getOrCreateStatus();
    UrlStatus status = urlStatusFile.getStatusJson().getOrCreateUrlStatus(downloadUrl);
    if (result.isSuccess()) {
      status.setSuccess(new UrlStatusState());
    } else if (result.isFailure()) {
      UrlStatusState error = new UrlStatusState();
      error.setCode(Integer.valueOf(result.getHttpStatusCode()));
      // String message = result.getHttpStatusCode() + " " + result.getUrl();
      // error.setMessage(message);
      status.setError(error);
    }
  }

  /**
   * Updates the tool's versions in the URL repository.
   *
   * @param urlRepository the URL repository to update
   */
  @Override
  public void update(UrlRepository urlRepository) {

    UrlTool tool = urlRepository.getOrCreateChild(getTool());
    UrlEdition edition = tool.getOrCreateChild(getEdition());
    updateExistingVersions(edition);
    Set<String> versions = getVersions();
    for (String version : versions) {
      version = mapVersion(version);
      if (version != null && edition.getChild(version) == null && !version.isEmpty()) {
        UrlVersion urlVersion = edition.getOrCreateChild(version);
        updateVersion(urlVersion);
        urlVersion.save();
      }
    }
  }

  /**
   * Update existing versions of the tool in the URL repository.
   *
   * @param edition the URL edition to update
   */
  protected void updateExistingVersions(UrlEdition edition) {

    Set<String> existingVersions = edition.getAllVersions();
    for (String version : existingVersions) {
      UrlVersion urlVersion = edition.getChild(version);
      if (urlVersion != null) {
        UrlStatusFile urlStatusFile = urlVersion.getOrCreateStatus();
        logger.info("Getting or creating Status for version {}", version);
        if (urlStatusFile.getStatusJson().isManual()) {
          logger.info("Version {} is manual, skipping update", version);
          continue;
        }
        updateVersion(urlVersion);
        urlVersion.save();
      }
    }
  }

  /**
   * Finds all currently available versions of the {@link UrlEdition tool edition}.
   *
   * @return the {@link Set} with all current versions.
   */
  protected abstract Set<String> getVersions();

  /**
   * @param version the original version (e.g. "v1.0").
   * @return the transformed version (e.g. "1.0") or {@code null} to filter and omit the given version.
   */
  protected String mapVersion(String version) {

    return version;
  }

  /**
   * @param version the version to add (e.g. "1.0").
   * @param versions the {@link Collection} with the versions to collect.
   */
  protected final void addVersion(String version, Collection<String> versions) {

    String mappedVersion = mapVersion(version);
    if (mappedVersion == null) {
      logger.debug("Filtered version {}", version);
    } else if (!mappedVersion.equals(version)) {
      logger.debug("Mapped version {} to {}", version, mappedVersion);
    }
    boolean added = versions.add(mappedVersion);
    if (!added) {
      logger.warn("Duplicate version {}", version);
    }
  }

  /**
   * Updates the version of a given URL version.
   *
   * @param urlVersion the URL version to be updated
   */
  protected abstract void updateVersion(UrlVersion urlVersion);

}
