package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.time.Instant;

public class URLStatusError {
	private Instant timestamp;
	private String message;

	public URLStatusError() {
		this(null);
	}

	public URLStatusError(String message) {
		this.timestamp = Instant.now();
		this.message = message;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}