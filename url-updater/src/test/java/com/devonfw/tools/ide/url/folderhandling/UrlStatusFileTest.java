package com.devonfw.tools.ide.url.folderhandling;

import java.nio.file.Paths;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.devonfw.tools.ide.url.folderhandling.jsonfile.StatusJson;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlStatus;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlStatusState;

/**
 * Test of {@link UrlStatusFile}.
 */
public class UrlStatusFileTest extends Assertions {

  /**
   * Test of {@link UrlStatusFile#getStatusJson()}.
   */
  @Test
  public void testReadJson() {

    // given
    UrlRepository repo = UrlRepository.load(Paths.get("src/test/resources/urls"));
    UrlTool tool = repo.getChild("docker");
    UrlEdition edition = tool.getChild("rancher");
    UrlVersion version = edition.getChild("1.6.2");
    // when
    UrlStatusFile status = version.getOrCreateStatus();
    StatusJson statusJson = status.getStatusJson();
    // then
    assertThat(statusJson.getUrls()).hasSize(1);
    UrlStatus urlStatus = statusJson.getUrls().values().iterator().next();
    UrlStatusState success = urlStatus.getSuccess();
    assertThat(success.getTimestamp()).isEqualTo(Instant.parse("2023-02-21T15:03:09.387386Z"));
    assertThat(success.getCode()).isNull();
    assertThat(success.getMessage()).isNull();
    UrlStatusState error = urlStatus.getError();
    assertThat(error.getTimestamp()).isEqualTo(Instant.parse("2023-01-01T23:59:59.999999Z"));
    assertThat(error.getCode()).isEqualTo(500);
    assertThat(error.getMessage()).isEqualTo("core dumped");
  }

}
