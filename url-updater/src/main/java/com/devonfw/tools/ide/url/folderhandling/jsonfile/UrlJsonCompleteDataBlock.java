package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UrlJsonCompleteDataBlock {

  private Manual manual;

  private Set<UrlJsonDataBlockForSpecificUrlFile> dataBlocks = new HashSet<UrlJsonDataBlockForSpecificUrlFile>();


  public UrlJsonCompleteDataBlock() {
    super();
  }

  public UrlJsonCompleteDataBlock(Manual manual, Set<UrlJsonDataBlockForSpecificUrlFile> dataBlocks) {

    this.manual = manual;
    this.dataBlocks = dataBlocks;
  }

  public Manual getManual() {

    return manual;
  }

  public Set<UrlJsonDataBlockForSpecificUrlFile> getDataBlocks() {

    return dataBlocks;
  }

  public void setManual(Manual manual) {

    this.manual = manual;
  }

  public void setDataBlocks(Set<UrlJsonDataBlockForSpecificUrlFile> dataBlocks) {

    this.dataBlocks = dataBlocks;
  }

  //Question if this method is necessary?
  public void addSingleDataBlock(UrlJsonDataBlockForSpecificUrlFile dataBlockToAdd) {

    this.dataBlocks.add(dataBlockToAdd);

  }
}
