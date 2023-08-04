package com.devonfw.tools.ide.version;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link VersionSegment}.
 */
public class VersionSegmentTest extends Assertions {

  /**
   * Test of {@link VersionSegment#ofEmpty()} and {@link VersionSegment#isEmpty()}.
   */
  @Test
  public void testEmpty() {

    // given + when
    VersionSegment empty = VersionSegment.ofEmpty();
    // then
    assertThat(empty.isEmpty()).isTrue();
    assertThat(empty.getSeparator()).isEmpty();
    assertThat(empty.getLetters()).isEmpty();
    assertThat(empty.getPhase()).isSameAs(VersionPhase.NONE);
    assertThat(empty.getDigits()).isEmpty();
    assertThat(empty.getNumber()).isEqualTo(-1);
    assertThat(VersionSegment.ofEmpty()).isSameAs(empty);
    assertThat(empty.getNextOrNull()).isNull();
    assertThat(empty.getNextOrEmpty()).isSameAs(empty);
  }

  /**
   * Test of constructor and getters with "01".
   */
  @Test
  public void testOne() {

    VersionSegment one = new VersionSegment("", "", "01");
    assertThat(one.getSeparator()).isEmpty();
    assertThat(one.getLetters()).isEmpty();
    assertThat(one.getPhase()).isSameAs(VersionPhase.NONE);
    assertThat(one.getDigits()).isEqualTo("01");
    assertThat(one.getNumber()).isEqualTo(1);
    assertThat(one.getNextOrNull()).isNull();
    assertThat(one.getNextOrEmpty()).isSameAs(VersionSegment.ofEmpty());
  }

  /**
   * Test of constructor and getters with ".rc1".
   */
  @Test
  public void testDotRc2() {

    VersionSegment one = new VersionSegment(".", "rc", "1");
    assertThat(one.getSeparator()).isEqualTo(".");
    assertThat(one.getLetters()).isEqualTo("rc");
    assertThat(one.getPhase()).isSameAs(VersionPhase.RELEASE_CANDIDATE);
    assertThat(one.getDigits()).isEqualTo("1");
    assertThat(one.getNumber()).isEqualTo(1);
    assertThat(one.getNextOrNull()).isNull();
    assertThat(one.getNextOrEmpty()).isSameAs(VersionSegment.ofEmpty());
  }

}
