package com.devonfw.tools.ide.url.updater.mavenapiclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Versioning {
	private String latest;
	private String release;
	private List<String> versions;
	private String lastUpdated;

	public Versioning() {
	} // added default constructor

	public Versioning(String latest, String release, List<String> versions, String lastUpdated) {
		this.latest = latest;
		this.release = release;
		this.versions = versions;
		this.lastUpdated = lastUpdated;
	}

	@JsonProperty("latest")  // added JsonProperty annotation
	public String getLatest() {
		return latest;
	}

	@JsonProperty("latest")  // added JsonProperty annotation
	public void setLatest(String latest) {
		this.latest = latest;
	}

	@JsonProperty("release")  // added JsonProperty annotation
	public String getRelease() {
		return release;
	}

	@JsonProperty("release")  // added JsonProperty annotation
	public void setRelease(String release) {
		this.release = release;
	}

	@JsonProperty("versions")  // added JsonProperty annotation
	public List<String> getVersions() {
		return versions;
	}

	@JsonProperty("versions")  // added JsonProperty annotation
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	@JsonProperty("lastUpdated")  // added JsonProperty annotation
	public String getLastUpdated() {
		return lastUpdated;
	}

	@JsonProperty("lastUpdated")  // added JsonProperty annotation
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}