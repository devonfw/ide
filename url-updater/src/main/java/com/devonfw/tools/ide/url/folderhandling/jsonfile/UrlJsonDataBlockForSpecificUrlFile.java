package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.nio.file.attribute.FileTime;
import java.util.Set;

import ch.qos.logback.core.testUtil.FileTestUtil;

public class UrlJsonDataBlockForSpecificUrlFile {

  Operatingsystem operatingSystem;

  Architecture architecture;

  private Set<Double> urlHashes;

  private String dateOfLastFileModificationAsString;

  public UrlJsonDataBlockForSpecificUrlFile() {

    super();
  }

  public UrlJsonDataBlockForSpecificUrlFile(Operatingsystem operatingSystem, Architecture architecture,
      Set<Double> urlHashes, String dateOfLastFileModificationAsString) {

    this.operatingSystem = operatingSystem;
    this.architecture = architecture;
    this.urlHashes = urlHashes;
    this.dateOfLastFileModificationAsString = dateOfLastFileModificationAsString;
  }

  public Operatingsystem getOperatingSystem() {

    return operatingSystem;
  }

  public Architecture getArchitecture() {

    return architecture;
  }

  public Set<Double> getUrlHashes() {

    return urlHashes;
  }

  public String getDateOfLastFileModificationAsString() {

    return dateOfLastFileModificationAsString;
  }

}
