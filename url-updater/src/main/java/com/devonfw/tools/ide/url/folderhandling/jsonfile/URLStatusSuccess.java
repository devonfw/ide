package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.time.Instant;

public class URLStatusSuccess {
	private String timestamp;

	public URLStatusSuccess() {
		this.timestamp = Instant.now().toString();
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}