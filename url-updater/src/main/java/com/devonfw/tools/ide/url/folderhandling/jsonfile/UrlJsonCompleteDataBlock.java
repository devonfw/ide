package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.HashSet;
import java.util.Set;

public class UrlJsonCompleteDataBlock {

	private Boolean manual;

	private Set<JsonDataBlockForSpecificUrl> urls = new HashSet<JsonDataBlockForSpecificUrl>();

	public UrlJsonCompleteDataBlock() {
		super();
	}

	public UrlJsonCompleteDataBlock(boolean manual, Set<JsonDataBlockForSpecificUrl> dataBlocks) {

		this.manual = manual;
		this.urls = dataBlocks;
	}

	public boolean getManual() {

		return manual;
	}

	public Set<JsonDataBlockForSpecificUrl> getUrls() {

		return urls;
	}

	public void setManual(Boolean manual) {

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
