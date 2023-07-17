package com.devonfw.tools.ide.integrationtests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.devonfw.tools.ide.url.updater.JsonUrlUpdater;
import com.devonfw.tools.ide.url.updater.UpdateManager;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

/**
 * Test class for integrations of the {@link UpdateManager}
 */
@WireMockTest(httpPort = 8080)
public class UpdateManagerIT extends Assertions {

    /**
     * Test resource location
     */
    private final static String testdataRoot = "src/test/resources/integrationtests/UpdateManager";

    /**
     * Test of {@link UpdateManager} using a simple {@link JsonUrlUpdater} for the creation of Android Studio download
     * URLs.
     *
     * @param tempDir Path to a temporary directory
     * @throws IOException test fails
     */
    @Test
    public void testUpdateManager(@TempDir Path tempDir) throws IOException {

        // given
        stubFor(get(urlMatching("/android-studio-releases-list.*")).willReturn(aResponse().withStatus(200).withBody(Files.readAllBytes(Paths.get(testdataRoot).resolve("android-version.json")))));

        stubFor(get(urlMatching("/edgedl/android/studio/ide-zips.*")).willReturn(aResponse().withStatus(200).withBody("aBody")));

        Path repoPath = tempDir;
        UrlRepository urlRepository = UrlRepository.load(repoPath);
        AndroidStudioUrlUpdaterMock updater = new AndroidStudioUrlUpdaterMock();

        // when
        updater.update(urlRepository);

        Path androidStudioVersionsPath = repoPath.resolve("android-studio").resolve("android-studio").resolve("2023.1.1.2");

        // then
        assertThat(androidStudioVersionsPath.resolve("status.json")).exists();
        assertThat(androidStudioVersionsPath.resolve("linux_x64.urls")).exists();
        assertThat(androidStudioVersionsPath.resolve("linux_x64.urls.sha256")).exists();
        assertThat(androidStudioVersionsPath.resolve("mac_arm64.urls")).exists();
        assertThat(androidStudioVersionsPath.resolve("mac_arm64.urls.sha256")).exists();
        assertThat(androidStudioVersionsPath.resolve("mac_x64.urls")).exists();
        assertThat(androidStudioVersionsPath.resolve("mac_x64.urls.sha256")).exists();
        assertThat(androidStudioVersionsPath.resolve("windows_x64.urls")).exists();
        assertThat(androidStudioVersionsPath.resolve("windows_x64.urls.sha256")).exists();

    }


}
