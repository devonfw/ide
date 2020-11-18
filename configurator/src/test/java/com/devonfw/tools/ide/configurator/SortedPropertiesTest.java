package com.devonfw.tools.ide.configurator;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link SortedProperties}.
 */
public class SortedPropertiesTest extends Assertions {

  @Test
  public void test() throws Exception {

    // given
    SortedProperties properties = new SortedProperties();
    properties.setProperty("zz", "top");
    properties.setProperty("middle", "man");
    properties.setProperty("alpha", "omega");
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    OutputStreamWriter out = new OutputStreamWriter(buffer);
    // when
    properties.store(out, null);
    // then
    String result = buffer.toString().replaceAll("#.*\n", ""); // remove invariant date header
    String newline = System.lineSeparator();
    assertThat(result).isEqualTo("alpha=omega" + newline //
        + "middle=man" + newline //
        + "zz=top" + newline);
  }

}
