package com.devonfw.tools.ide.integrationtest.androidstudio;

import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.devonfw.tools.ide.url.updater.JsonUrlUpdater;
import com.devonfw.tools.ide.url.updater.androidstudio.AndroidStudioUrlUpdater;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Test class for integrations of the {@link AndroidStudioUrlUpdater
 */
@WireMockTest(httpPort = 8080)
public class AndroidStudioJsonUrlUpdaterIT extends Assertions {

  /**
   * Test resource location
   */
  private final static String testdataRoot = "src/test/resources/integrationtest/AndroidStudioJsonUrlUpdater";

  /** This is the SHA256 checksum of aBody (a placeholder body which gets returned by WireMock) */
  private static final String EXPECTED_ABODY_CHECKSUM = "2bd115c7425d128e24a6cdfc9b6f82762d6e1b7d9868d0974faeaa18b54c3de3";

  /**
   * Test of {@link JsonUrlUpdater} for the creation of {@link AndroidStudioUrlUpdater download URLs and checksums.
   *
   * @param tempDir Path to a temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testJsonUrlUpdaterCreatesDownloadUrlsAndChecksums(@TempDir Path tempDir) throws IOException {

    // given
    //stubFor(get(urlMatching("/android-studio-releases-list.*")).willReturn(aResponse().withStatus(200)
    //    .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("android-version.json")))));

    stubFor(get(urlMatching("/idea*")).willReturn(aResponse().withStatus(200)
        .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("intellij-version.json")))));

    stubFor(any(urlMatching("/idea/idea*")).willReturn(
        aResponse().withStatus(200).withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    //AndroidStudioUrlUpdaterMock updater = new AndroidStudioUrlUpdaterMock();
    IntellijUrlUpdaterMock updater = new IntellijUrlUpdaterMock();

    // when
    updater.update(urlRepository);

    //Path androidStudioVersionsPath = tempDir.resolve("android-studio").resolve("android-studio").resolve("2023.1.1.2");
    Path intellijVersionsPath = tempDir.resolve("intellij").resolve("community").resolve("2023.1.1");

    // then
    assertThat(intellijVersionsPath.resolve("status.json")).exists();
    assertThat(intellijVersionsPath.resolve("linux_x64.urls")).exists();
    assertThat(intellijVersionsPath.resolve("linux_x64.urls.sha256")).exists();
    assertThat(intellijVersionsPath.resolve("mac_arm64.urls")).exists();
    assertThat(intellijVersionsPath.resolve("mac_arm64.urls.sha256")).exists();
    assertThat(intellijVersionsPath.resolve("mac_x64.urls")).exists();
    assertThat(intellijVersionsPath.resolve("mac_x64.urls.sha256")).exists();
    assertThat(intellijVersionsPath.resolve("windows_x64.urls")).exists();
    assertThat(intellijVersionsPath.resolve("windows_x64.urls.sha256")).exists();

  }

  /**
   * Test if the {@link JsonUrlUpdater} for
   * {@link AndroidStudioUrlUpdater can handle downloads with missing checksums (generate checksum from download file if
   * no checksum was provided)
   *
   * @param tempDir Path to a temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testJsonUrlUpdaterWithMissingDownloadsDoesNotCreateVersionFolder(@TempDir Path tempDir)
      throws IOException {

    // given
    //stubFor(get(urlMatching("/android-studio-releases-list.*")).willReturn(aResponse().withStatus(200)
    //    .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("android-version.json")))));

    stubFor(get(urlMatching("/idea*")).willReturn(aResponse().withStatus(200)
        .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("intellij-version.json")))));

    stubFor(get(urlMatching("/idea/idea*")).willReturn(aResponse().withStatus(404)));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    // AndroidStudioUrlUpdaterMock updater = new AndroidStudioUrlUpdaterMock();
    IntellijUrlUpdaterMock updater = new IntellijUrlUpdaterMock();
    // when
    updater.update(urlRepository);

    Path androidStudioVersionsPath = tempDir.resolve("intellij").resolve("community").resolve("2023.1.3");

    // then
    assertThat(androidStudioVersionsPath).doesNotExist();

  }

  /**
   * Test if the {@link JsonUrlUpdater} for {@link AndroidStudioUrlUpdater} can handle downloads with missing checksums
   * (generate checksum from download file if no checksum was provided)
   *
   * @param tempDir Path to a temporary directory
   * @throws IOException test fails
   */
  @Test
  public void testJsonUrlUpdaterWithMissingChecksumGeneratesChecksum(@TempDir Path tempDir) throws IOException {

    // given
    stubFor(get(urlMatching("/idea*")).willReturn(aResponse().withStatus(200)
        .withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("intellij-version-withoutchecksum.json")))));

    stubFor(any(urlMatching("/idea/idea*")).willReturn(
        aResponse().withStatus(200).withBody("aBody")));

    UrlRepository urlRepository = UrlRepository.load(tempDir);
    //AndroidStudioUrlUpdaterMock updater = new AndroidStudioUrlUpdaterMock();
    IntellijUrlUpdaterMock updater = new IntellijUrlUpdaterMock();

    // when
    updater.update(urlRepository);

    Path androidStudioVersionsPath = tempDir.resolve("intellij").resolve("community").resolve("2023.1.1");

    // then
    assertThat(androidStudioVersionsPath.resolve("windows_x64.urls.sha256")).exists()
        .hasContent(EXPECTED_ABODY_CHECKSUM);

  }
}
