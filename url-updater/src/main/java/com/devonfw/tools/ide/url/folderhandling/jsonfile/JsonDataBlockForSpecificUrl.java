package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.Set;

public class JsonDataBlockForSpecificUrl {

	private Set<Integer> urlHashes;

	public JsonDataBlockForSpecificUrl() {

		super();
	}

	// TODO: Make one block per url, not per file with hashes for the urls in one
	// list.
	public JsonDataBlockForSpecificUrl(Set<Integer> urlHashes) {

		this.urlHashes = urlHashes;
	}

	public Set<Integer> getUrlHashes() {

		return urlHashes;
	}

}
