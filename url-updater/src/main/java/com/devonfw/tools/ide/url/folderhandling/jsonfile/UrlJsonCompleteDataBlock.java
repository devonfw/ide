package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.HashSet;
import java.util.Set;

/*
 * Complete data block read from a status JSON file, or to be written into a status  JSON file,
 * consisting of a manual flag as well as of data blocks for data regarding the download urls.
 */
public class UrlJsonCompleteDataBlock {

  private boolean manual;

  private Set<JsonDataBlockForSpecificUrl> urls = new HashSet<JsonDataBlockForSpecificUrl>();

  public UrlJsonCompleteDataBlock() {

    super();
  }

  public UrlJsonCompleteDataBlock(boolean manual, Set<JsonDataBlockForSpecificUrl> dataBlocks) {

    this.manual = manual;
    this.urls = dataBlocks;
  }

  public boolean isManual() {

    return this.manual;
  }

  public Set<JsonDataBlockForSpecificUrl> getUrls() {

    return this.urls;
  }

  public void setManual(boolean manual) {

    this.manual = manual;
  }

  public void setUrls(Set<JsonDataBlockForSpecificUrl> dataBlocks) {

    this.urls = dataBlocks;
  }

  // For code testing
  public void addSingleDataBlock(JsonDataBlockForSpecificUrl dataBlockToAdd) {

    this.urls.add(dataBlockToAdd);

  }
}
