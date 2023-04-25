package com.devonfw.tools.ide.url.updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

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
import java.util.Set;

import com.devonfw.tools.ide.url.folderhandling.UrlChecksum;
import com.devonfw.tools.ide.url.folderhandling.UrlDownloadFile;
import com.devonfw.tools.ide.url.folderhandling.UrlEdition;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;
import com.devonfw.tools.ide.url.folderhandling.UrlStatusFile;
import com.devonfw.tools.ide.url.folderhandling.UrlTool;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlStatus;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlStatusState;

/**
 * An abstract class representing a web crawler that implements the Updater interface.
 * Contains methods for retrieving response bodies from URLs, updating tool versions, and checking if download URLs work.
 */
public abstract class AbstractCrawler implements Updater {
	private static final Logger logger = LoggerFactory.getLogger(AbstractCrawler.class);
	protected final HttpClient client = HttpClient.newBuilder().build();

	/**
	 * Retrieves the response body from a given URL.
	 *
	 * @param url the URL to retrieve the response body from.
	 * @return a string representing the response body.
	 * @throws IllegalStateException if the response body could not be retrieved.
	 */
	protected String doGetResponseBody(String url) {
		try {
			HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			return client.send(request1, HttpResponse.BodyHandlers.ofString()).body();
		} catch (IOException | InterruptedException exception) {
			throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
		} catch (IllegalArgumentException e) {
			logger.error("URLStatusError while getting response body from url {}", url, e);
			return "";
		}
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
		UrlRequestResult result = doCheckIfDownloadUrlWorks(downloadUrl);
		if (result.isSuccess()) {
			UrlDownloadFile urlDownloadFile = urlVersion.getOrCreateUrls(osString, arch);
			urlDownloadFile.addUrl(downloadUrl);
			doCreateOrRefreshStatusJson(result, urlVersion, downloadUrl);
			urlVersion.save();

			//generate checksum of download file
			if (result.getHttpStatusCode() == 200) {
			  UrlChecksum urlChecksum = urlVersion.getOrCreateChecksum(urlDownloadFile.getName());
			  doGenerateChecksum(urlChecksum, downloadUrl);
			}
			return true;
		} else {
			//check if folder of urlVersion exists
			Path folderPath = Paths.get(urlVersion.getPath().toString());
			if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
				doCreateOrRefreshStatusJson(result, urlVersion, downloadUrl);
				urlVersion.save();
			}
			return false;
		}
	}

	  /**
	   * @param url the url of the download file
	   * @return the input stream of requested url
	   */
	  private InputStream doGetResponseInputStream(String url) {

	    try {
	      HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
	      return this.client.send(request1, HttpResponse.BodyHandlers.ofInputStream()).body();
	    } catch (IOException | InterruptedException exception) {
	      throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
	    } catch (IllegalArgumentException e) {
	      logger.error("Error while getting response body from url {}", url, e);
	      return null;
	    }
	  }

	  /**
	   * @param inputStream the input stream of requested url
	   * @return checksum of input stream as string
	   */
	  private String doGenerateChecksumFromInputStream(InputStream inputStream) {

	    try {
	      MessageDigest md = MessageDigest.getInstance(UrlChecksum.HASH_ALGORITHM);

	      byte[] buffer = new byte[8192];
	      int bytesRead;
	      while ((bytesRead = inputStream.read(buffer)) != -1) {
	        md.update(buffer, 0, bytesRead);
	      }
	      inputStream.close();

	      byte[] digestBytes = md.digest();
	      String checksum = toHexString(digestBytes);
	      return checksum;
	    } catch (IOException e) {
	      throw new IllegalStateException("Failed to read input stream of download.", e);
	    } catch (NoSuchAlgorithmException e) {
	      throw new IllegalStateException("No such hash algorithm " + UrlChecksum.HASH_ALGORITHM, e);
	    }
	  }

	  /**
	   * @param bytes the byte array to convert in hex String
	   * @return converted string in hexadecimal format
	   */
	  private static String toHexString(byte[] bytes) {

	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	      sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	  }

	  /**
	   * @param urlChecksum the {@link UrlChecksum} file.
	   * @param downloadUrl the url of the download file.
	   */
	  public void doGenerateChecksum(UrlChecksum urlChecksum, String downloadUrl) {

	    urlChecksum.setChecksum(doGenerateChecksumFromInputStream(doGetResponseInputStream(downloadUrl)));
	    // urlChecksum.save();
	  }

	/**
	 * Checks if a download URL works and if the file is available for download.
	 *
	 * @param downloadUrl the URL to check.
	 * @return a URLRequestResult object representing the success or failure of the URL check.
	 */
	protected UrlRequestResult doCheckIfDownloadUrlWorks(String downloadUrl) {
		// Do Head request to check if the download url works and if the file is available for download
		UrlRequestResult URLRequestResult;
		Level logLevel = Level.INFO;
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(downloadUrl)).method("HEAD", HttpRequest.BodyPublishers.noBody()).timeout(Duration.ofSeconds(5)).build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			// Return the success or failure URLRequestResult
			URLRequestResult = response.statusCode() >= 200 && response.statusCode() < 400 ? new UrlRequestResult(true, response.statusCode(), downloadUrl) : new UrlRequestResult(false, response.statusCode(), downloadUrl);
		} catch (IOException | InterruptedException e) {
			URLRequestResult = new UrlRequestResult(false, 500, downloadUrl);
		}

		if (URLRequestResult.isFailure()) {
			logLevel = Level.WARN;
		}

		logger.atLevel(logLevel).log("Download url: {} is {} with status code: {}", downloadUrl, URLRequestResult.isSuccess() ? "available" : "not available", URLRequestResult.getHttpStatusCode());
		return URLRequestResult;

	}

	/**
	 * Creates or refreshes the status JSON file for a given UrlVersion instance based on the URLRequestResult of checking if a download URL works.
	 *
	 * @param result the {@link UrlRequestResult} instance indicating whether the download URL works.
	 * @param urlVersion       the UrlVersion instance to create or refresh the status JSON file for.
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
		UrlTool tool = urlRepository.getOrCreateChild(getToolName());
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

	protected abstract String getToolName();

	protected String getEdition() {
		return getToolName();
	}

	protected final String getToolWithEdition() {

	  String tool = getToolName();
	  String edition = getEdition();
	  if (tool.equals(edition)) {
	    return tool;
	  }
	  return tool + "/" + edition;
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

	protected abstract Set<String> getVersions();

	/**
	 * @param version original version.
	 * @return the transformed version
	 */
	protected String mapVersion(String version) {
		return version;
	}

	/**
	 * Updates the version of a given URL version.
	 *
	 * @param urlVersion the URL version to be updated
	 */
	protected abstract void updateVersion(UrlVersion urlVersion);

}
