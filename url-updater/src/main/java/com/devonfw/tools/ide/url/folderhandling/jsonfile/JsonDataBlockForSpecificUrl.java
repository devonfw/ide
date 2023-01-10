package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.Set;

public class JsonDataBlockForSpecificUrl {

  private Set<Double> urlHashes;

  public JsonDataBlockForSpecificUrl() {

    super();
  }

  //TO DO: make one block per url, not per file with hashes for the urls in one list.
  public JsonDataBlockForSpecificUrl(
      Set<Double> urlHashes) {

    this.urlHashes = urlHashes;
  }


  public Set<Double> getUrlHashes() {

    return urlHashes;
  }

}
