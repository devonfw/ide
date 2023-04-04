package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatusJson {
	private boolean manual;
	private Map<String, URLStatus> urlStatuses;

	public StatusJson() {
		this.manual = false;
		this.urlStatuses = new LinkedHashMap<>();
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public Map<String, URLStatus> getUrlStatuses() {
		return urlStatuses;
	}

	public void setUrlStatuses(Map<String, URLStatus> urlStatuses) {
		this.urlStatuses = urlStatuses;
	}

	public void addUrlStatus(String urlHash, URLStatus status) {
		this.urlStatuses.put(urlHash, status);
	}
}