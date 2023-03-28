package com.devonfw.tools.ide.url.folderhandling.jsonfile;

public class URLStatus {
	private URLStatusSuccess URLStatusSuccess;
	private URLStatusError URLStatusError;

	public URLStatus() {
	}

	public URLStatusSuccess getSuccess() {
		return URLStatusSuccess;
	}

	public void setSuccess(URLStatusSuccess URLStatusSuccess) {
		this.URLStatusSuccess = URLStatusSuccess;
	}

	public URLStatusError getError() {
		return URLStatusError;
	}

	public void setError(URLStatusError URLStatusError) {
		this.URLStatusError = URLStatusError;
	}
}


