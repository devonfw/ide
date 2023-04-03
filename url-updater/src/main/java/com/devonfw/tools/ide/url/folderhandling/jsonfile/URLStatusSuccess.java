package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.time.Instant;

public class URLStatusSuccess {
	private Instant timestamp;

	public URLStatusSuccess() {
		this.timestamp = Instant.now();
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
}