package com.devonfw.tools.ide.version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link VersionIdentifier}.
 */
public class VersionIdentifierTest extends Assertions {

  /**
   * Test of {@link VersionIdentifier#of(String)}.
   */
  @Test
  public void testOf() {

    // test exotic version number
    // given
    String version = "1.0-release-candidate2_-.HF1";
    // when
    VersionIdentifier vid = VersionIdentifier.of(version);
    // then
    VersionSegment segment1 = vid.getStart();
    assertThat(segment1.getSeparator()).isEmpty();
    assertThat(segment1.getLetters()).isEmpty();
    assertThat(segment1.getPhase()).isSameAs(VersionPhase.NONE);
    assertThat(segment1.getNumber()).isEqualTo(1);
    assertThat(segment1).hasToString("1");
    VersionSegment segment2 = segment1.getNextOrNull();
    assertThat(segment2.getSeparator()).isEqualTo(".");
    assertThat(segment2.getLetters()).isEmpty();
    assertThat(segment2.getPhase()).isSameAs(VersionPhase.NONE);
    assertThat(segment2.getNumber()).isEqualTo(0);
    assertThat(segment2).hasToString(".0");
    VersionSegment segment3 = segment2.getNextOrNull();
    assertThat(segment3.getSeparator()).isEqualTo("-");
    assertThat(segment3.getLetters()).isEqualTo("release-candidate");
    assertThat(segment3.getPhase()).isSameAs(VersionPhase.RELEASE_CANDIDATE);
    assertThat(segment3.getNumber()).isEqualTo(2);
    assertThat(segment3).hasToString("-release-candidate2");
    VersionSegment segment4 = segment3.getNextOrNull();
    assertThat(segment4.getSeparator()).isEqualTo("_-.");
    assertThat(segment4.getLetters()).isEqualTo("HF");
    assertThat(segment4.getPhase()).isSameAs(VersionPhase.HOT_FIX);
    assertThat(segment4.getNumber()).isEqualTo(1);
    assertThat(segment4).hasToString("_-.HF1");
  }

  /**
   * Test of {@link VersionIdentifier#isValid() valid} versions.
   */
  @Test
  public void testValid() {

    String[] validVersions = { "1.0", "0.1", "2023.08.001", "2023-06-M1", "11.0.4_11.4", "5.2.23.RELEASE" };
    for (String version : validVersions) {
      VersionIdentifier vid = VersionIdentifier.of(version);
      assertThat(vid.isValid()).as(version).isTrue();
      assertThat(vid).hasToString(version);
    }
  }

  /**
   * Test of in{@link VersionIdentifier#isValid() valid} versions.
   */
  @Test
  public void testInvalid() {

    String[] invalidVersions = { "0", "0.0", "1.0.pineapple-pen", "1.0-rc", ".1.0", "1.-0", "RC1", "Beta1", "donut" };
    for (String version : invalidVersions) {
      VersionIdentifier vid = VersionIdentifier.of(version);
      assertThat(vid.isValid()).as(version).isFalse();
      assertThat(vid).hasToString(version);
    }
  }

  /**
   * Test of {@link VersionIdentifier} with canonical version numbers and safe order.
   */
  @Test
  public void testCompare() {

    String[] versions = { "0.1", "0.2-SNAPSHOT", "0.2-nb5", "0.2-a", "0.2-alpha1", "0.2-beta", "0.2-b2", "0.2.M1",
    "0.2M9", "0.2M10", "0.2-rc1", "0.2-RC2", "0.2", "0.2-fix9", "0.2-hf1", "0.3", "0.3.1", "1", "1.0", "10-alpha1" };
    List<VersionIdentifier> vids = new ArrayList<>(versions.length);
    for (String version : versions) {
      VersionIdentifier vid = VersionIdentifier.of(version);
      vids.add(vid);
      assertThat(vid).hasToString(version);
    }
    Collections.shuffle(vids);
    Collections.sort(vids);
    for (int i = 0; i < versions.length; i++) {
      String version = versions[i];
      VersionIdentifier vid = vids.get(i);
      assertThat(vid).hasToString(version);
    }
  }

}
