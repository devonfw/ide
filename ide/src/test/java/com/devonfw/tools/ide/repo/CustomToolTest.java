package com.devonfw.tools.ide.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.devonfw.tools.ide.version.VersionIdentifier;

/**
 * Test of {@link CustomTool}.
 */
public class CustomToolTest extends Assertions {

  /**
   * Test of {@link CustomTool}.
   */
  @Test
  public void testAgnostic() {

    // arrange
    String name = "jboss-eap";
    VersionIdentifier version = VersionIdentifier.of("7.4.5.GA");
    String repositoryUrl = "https://host.domain.tld:8443/folder/repo";
    String checksum = "4711";
    boolean osAgnostic = false;
    boolean archAgnostic = false;
    // act
    CustomTool tool = new CustomTool(name, version, osAgnostic, archAgnostic, repositoryUrl, checksum, null);
    // assert
    assertThat(tool.getTool()).isEqualTo(name);
    assertThat(tool.getVersion()).isSameAs(version);
    assertThat(tool.isOsAgnostic()).isEqualTo(osAgnostic);
    assertThat(tool.isArchAgnostic()).isEqualTo(archAgnostic);
    assertThat(tool.getRepositoryUrl()).isEqualTo(repositoryUrl);
    assertThat(tool.getUrl())
        .isEqualTo("https://host.domain.tld:8443/folder/repo/jboss-eap/7.4.5.GA/jboss-eap-7.4.5.GA.tgz");
    assertThat(tool.getChecksum()).isEqualTo(checksum);
  }

  /**
   * Test of {@link CustomTool}.
   */
  @Test
  public void testSpecific() {

    // arrange
    String name = "firefox";
    VersionIdentifier version = VersionIdentifier.of("7.4.5.GA");
    String repositoryUrl = "https://host.domain.tld:8443/folder/repo";
    String checksum = "4711";
    boolean osAgnostic = true;
    boolean archAgnostic = true;
    // act
    CustomTool tool = new CustomTool(name, version, osAgnostic, archAgnostic, repositoryUrl, checksum, null);
    // assert
    assertThat(tool.getTool()).isEqualTo(name);
    assertThat(tool.getVersion()).isSameAs(version);
    assertThat(tool.isOsAgnostic()).isEqualTo(osAgnostic);
    assertThat(tool.isArchAgnostic()).isEqualTo(archAgnostic);
    assertThat(tool.getRepositoryUrl()).isEqualTo(repositoryUrl);
    assertThat(tool.getUrl())
        .isEqualTo("https://host.domain.tld:8443/folder/repo/jboss-eap/7.4.5.GA/jboss-eap-7.4.5.GA.tgz");
    assertThat(tool.getChecksum()).isEqualTo(checksum);
  }

  // public void testRepositoryId() {
  //
  // // arrange
  //
  // String name = "jboss-eap";
  // VersionIdentifier version = VersionIdentifier.of("7.4.5.GA");
  // String repositoryUrl = "https://host.domain.tld:port/folder/räpö$itöry+name/ochn%C3%B6n%F6";
  // // act
  // CustomTool tool = new CustomTool(name, version, false, false, repositoryUrl, null, null);
  // // assert
  // assertThat(tool.getTool()).isEqualTo(name);
  // assertThat(tool.getVersion()).isSameAs(version);
  // assertThat(tool.getRepositoryUrl()).isEqualTo(repositoryUrl);
  // assertThat(tool.getUrl()).isEqualTo("host.domain.tld_port/folder_r_p__it_ry_name_ochn_n_");
  // }

}
