package com.devonfw.tools.ide.integrationtest.pip;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.nio.file.Path;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.devonfw.tools.ide.integrationtest.AbstractUrlUpdaterTest;
import com.devonfw.tools.ide.integrationtest.UrlUpdaterMockSingle;
import com.devonfw.tools.ide.url.model.file.json.StatusJson;
import com.devonfw.tools.ide.url.model.file.json.UrlStatus;
import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest(httpPort = 8080)
public class PipUrlUpdaterTest extends AbstractUrlUpdaterTest {

  /**
   * Tests if the {@link com.devonfw.tools.ide.url.updater.pip.PipUrlUpdater} will successfully resolve a server with a
   * Content-Type:text header response.
   * <p>
   * See: <a href="https://github.com/devonfw/ide/issues/1343">#1343</a> for reference.
   *
   * @param tempDir Temporary directory
   */
  @Test
  public void testPipUrlUpdaterWithTextContentTypeWillSucceed(@TempDir Path tempDir) {

    // given
    stubFor(any(urlMatching("/pip/.*"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    PipUrlUpdaterMock updater = new PipUrlUpdaterMock();

    String statusUrl = "http://localhost:8080/pip/1.0/get-pip.py";

    // when
    updater.update(urlRepository);

    Path versionsPath = tempDir.resolve("pip").resolve("pip").resolve("1.0");

    // then
    assertThat(versionsPath.resolve("status.json")).exists();

    StatusJson statusJson = getStatusJson(versionsPath);
    UrlStatus urlStatus = statusJson.getOrCreateUrlStatus(statusUrl);
    Instant successTimestamp = urlStatus.getSuccess().getTimestamp();

    assertThat(successTimestamp).isNotNull();

  }

  /**
   * Tests if the {@link com.devonfw.tools.ide.url.updater.pip.PipUrlUpdater} will fail resolving a server with a
   * Content-Type:text header response.
   * <p>
   * See: <a href="https://github.com/devonfw/ide/issues/1343">#1343</a> for reference.
   *
   * @param tempDir Temporary directory
   */
  @Test
  public void testUrlUpdaterWithTextContentTypeWillNotCreateStatusJson(@TempDir Path tempDir) {

    // given
    stubFor(any(urlMatching("/os/.*"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    UrlUpdaterMockSingle updater = new UrlUpdaterMockSingle();

    // when
    updater.update(urlRepository);

    Path versionsPath = tempDir.resolve("mocked").resolve("mocked").resolve("1.0");

    // then
    assertThat(versionsPath.resolve("status.json")).doesNotExist();

  }
}
