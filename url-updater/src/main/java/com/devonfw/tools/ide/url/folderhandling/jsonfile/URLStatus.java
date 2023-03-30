package com.devonfw.tools.ide.url.folderhandling.jsonfile;

public class URLStatus {
	private URLStatusSuccess urlStatusSuccess;
	private URLStatusError urlStatusError;

	public URLStatus() {
	}

	public URLStatusSuccess getSuccess() {
		return urlStatusSuccess;
	}

	public void setSuccess(URLStatusSuccess urlStatusSuccess) {
		this.urlStatusSuccess = urlStatusSuccess;
	}

	public URLStatusError getError() {
		return urlStatusError;
	}

	public void setError(URLStatusError URLStatusError) {
		this.urlStatusError = URLStatusError;
	}
}


