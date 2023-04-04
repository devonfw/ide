package com.devonfw.tools.ide.url.updater.githubapiclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubJsonItem {
	private String ref;

	public GithubJsonItem() {
	}

	public GithubJsonItem(String ref) {
		this.ref = ref;
	}

	@JsonProperty("ref")
	public String getRef() {
		return ref;
	}

	@JsonProperty("ref")
	public void setRef(String ref) {
		this.ref = ref;
	}
}