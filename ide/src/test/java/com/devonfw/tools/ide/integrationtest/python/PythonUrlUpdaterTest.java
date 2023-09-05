package com.devonfw.tools.ide.integrationtest.python;

import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@WireMockTest(httpPort = 8080)
public class PythonUrlUpdaterTest extends Assertions {

  private final static String testdataRoot = "src/test/resources/integrationtest/PythonJsonUrlUpdater";

  /**
   * Test Python JsonUrlUpdater
   *
   * @param tempPath Path to a temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testPythonURl(@TempDir Path tempPath) throws IOException {
    // given
    stubFor(get(urlMatching("/actions/python-versions/main/.*")).willReturn(aResponse().withStatus(200)
        .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("python-version.json")))));

    stubFor(any(urlMatching("/actions/python-versions/releases/download.*")).willReturn(
        aResponse().withStatus(200).withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempPath);
    PythonUrlUpdaterMock pythonUpdaterMock = new PythonUrlUpdaterMock();
    pythonUpdaterMock.update(urlRepository);
    Path pythonPath = tempPath.resolve("python").resolve("python").resolve("3.12.0-beta.2");

    assertThat(pythonPath.resolve("status.json")).exists();
    assertThat(pythonPath.resolve("linux_x64.urls")).exists();
    assertThat(pythonPath.resolve("linux_x64.urls.sha256")).exists();
    assertThat(pythonPath.resolve("mac_arm64.urls")).exists();
    assertThat(pythonPath.resolve("mac_arm64.urls.sha256")).exists();
    assertThat(pythonPath.resolve("windows_x64.urls")).exists();
    assertThat(pythonPath.resolve("windows_x64.urls.sha256")).exists();

  }
}
