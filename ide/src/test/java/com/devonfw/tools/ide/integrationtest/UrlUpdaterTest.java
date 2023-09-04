package com.devonfw.tools.ide.integrationtest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.devonfw.tools.ide.json.mapping.JsonMapping;
import com.devonfw.tools.ide.url.model.file.json.StatusJson;
import com.devonfw.tools.ide.url.model.file.json.UrlStatus;
import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest(httpPort = 8080)
public class UrlUpdaterTest extends Assertions {

  /**
   * Test resource location
   */
  private final static String testdataRoot = "src/test/resources/integrationtest/UrlUpdater";

  /**
   * Tests if the {@link com.devonfw.tools.ide.url.updater.UrlUpdater} can automatically add a missing OS (in this case
   * the linux_x64)
   *
   * @param tempDir Temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testUrlUpdaterMissingOsGetsAddedAutomatically(@TempDir Path tempDir) throws IOException {

    stubFor(any(urlMatching("/os/.*")).willReturn(aResponse().withStatus(200).withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    UrlUpdaterMock updater = new UrlUpdaterMock();

    // when
    updater.update(urlRepository);

    Path versionsPath = tempDir.resolve("mocked").resolve("mocked").resolve("1.0");

    // then
    assertThat(versionsPath.resolve("status.json")).exists();
    assertThat(versionsPath.resolve("linux_x64.urls")).exists();
    assertThat(versionsPath.resolve("linux_x64.urls.sha256")).exists();
    assertThat(versionsPath.resolve("mac_arm64.urls")).exists();
    assertThat(versionsPath.resolve("mac_arm64.urls.sha256")).exists();
    assertThat(versionsPath.resolve("mac_x64.urls")).exists();
    assertThat(versionsPath.resolve("mac_x64.urls.sha256")).exists();
    assertThat(versionsPath.resolve("windows_x64.urls")).exists();
    assertThat(versionsPath.resolve("windows_x64.urls.sha256")).exists();

    Files.deleteIfExists(versionsPath.resolve("linux_x64.urls"));
    Files.deleteIfExists(versionsPath.resolve("linux_x64.urls.sha256"));

    // re-initialize UrlRepository
    UrlRepository urlRepositoryNew = UrlRepository.load(tempDir);
    updater.update(urlRepositoryNew);

    assertThat(versionsPath.resolve("linux_x64.urls")).exists();
    assertThat(versionsPath.resolve("linux_x64.urls.sha256")).exists();

  }

  /**
   * Tests if the timestamps of the status.json get updated properly. Creates an initial status.json with a success
   * timestamp. Updates the status.json with an error timestamp and compares it with the success timestamp. Updates the
   * status.json with a final success timestamp and compares it with the error timestamp.
   * 
   * @param tempDir Temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testUrlUpdaterStatusJsonRefresh(@TempDir Path tempDir) throws IOException {

    stubFor(any(urlMatching("/os/.*")).willReturn(aResponse().withStatus(200).withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    UrlUpdaterMockSingle updater = new UrlUpdaterMockSingle();

    String statusUrl = "http://localhost:8080/os/windows_x64_url.tgz";

    // when
    updater.update(urlRepository);

    Path versionsPath = tempDir.resolve("mocked").resolve("mocked").resolve("1.0");

    // then
    assertThat(versionsPath.resolve("status.json")).exists();

    StatusJson statusJson = getStatusJson(versionsPath);

    UrlStatus urlStatus = statusJson.getOrCreateUrlStatus(statusUrl);

    Instant successTimestamp = urlStatus.getSuccess().getTimestamp();

    assertThat(successTimestamp).isNotNull();

    stubFor(any(urlMatching("/os/.*")).willReturn(aResponse().withStatus(404)));

    // re-initialize UrlRepository for error timestamp
    UrlRepository urlRepositoryWithError = UrlRepository.load(tempDir);
    updater.update(urlRepositoryWithError);

    statusJson = getStatusJson(versionsPath);

    urlStatus = statusJson.getOrCreateUrlStatus(statusUrl);
    successTimestamp = urlStatus.getSuccess().getTimestamp();
    Instant errorTimestamp = urlStatus.getError().getTimestamp();
    Integer errorCode = urlStatus.getError().getCode();

    assertThat(errorCode).isEqualTo(404);
    assertThat(errorTimestamp).isGreaterThan(successTimestamp);

    stubFor(any(urlMatching("/os/.*")).willReturn(aResponse().withStatus(200).withBody("aBody")));

    // re-initialize UrlRepository for error timestamp
    UrlRepository urlRepositoryWithSuccess = UrlRepository.load(tempDir);
    updater.update(urlRepositoryWithSuccess);

    assertThat(versionsPath.resolve("status.json")).exists();

    statusJson = getStatusJson(versionsPath);

    urlStatus = statusJson.getOrCreateUrlStatus(statusUrl);

    successTimestamp = urlStatus.getSuccess().getTimestamp();
    errorTimestamp = urlStatus.getError().getTimestamp();
    errorCode = urlStatus.getError().getCode();

    assertThat(errorCode).isEqualTo(404);
    assertThat(successTimestamp).isGreaterThan(errorTimestamp);

  }

  private static StatusJson getStatusJson(Path versionsPath) {

    ObjectMapper MAPPER = JsonMapping.create();
    StatusJson statusJson = new StatusJson();
    Path statusJsonPath = versionsPath.resolve("status.json");

    if (Files.exists(statusJsonPath)) {
      try (BufferedReader reader = Files.newBufferedReader(statusJsonPath)) {
        statusJson = MAPPER.readValue(reader, StatusJson.class);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to load " + statusJsonPath, e);
      }
    }
    return statusJson;
  }

}
