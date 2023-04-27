package com.devonfw.tools.ide.url.updater.terraform;

import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.GithubUrlUpdater;

/**
 * {@link GithubUrlUpdater} for terraform.
 */
public class TerraformUrlUpdater extends GithubUrlUpdater {
  @Override
  protected String getTool() {

    return "terraform";
  }

  @Override
  protected String getGithubOrganization() {

    return "hashicorp";
  }

  @Override
  protected String mapVersion(String version) {

    return version.replace("v", "");
  }

  @Override
  protected void updateVersion(UrlVersion urlVersion) {

    String baseUrl = "https://releases.hashicorp.com/terraform/${version}/terraform_${version}_";
    doUpdateVersion(urlVersion, baseUrl + "windows_amd64.zip", WINDOWS);
    doUpdateVersion(urlVersion, baseUrl + "linux_amd64.zip", LINUX);
    doUpdateVersion(urlVersion, baseUrl + "darwin_amd64.zip", MAC);
    doUpdateVersion(urlVersion, baseUrl + "darwin_arm64.zip", MAC, ARM64);
  }

}
