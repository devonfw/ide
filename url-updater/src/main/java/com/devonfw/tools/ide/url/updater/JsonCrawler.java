package com.devonfw.tools.ide.url.updater;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public abstract class JsonCrawler<J extends JsonObject> extends AbstractCrawler {

	private final Logger logger = LoggerFactory.getLogger(JsonCrawler.class.getName());

	@Override
	protected Set<String> getVersions() {
		String url = doGetVersionUrl();
		Set<String> versions = new HashSet<>();
		try {
			String response = doGetResponseBody(url);
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			J jsonObject = mapper.readValue(response, getJsonObjectType());
			collectVersionsFromJson(jsonObject, versions);
			logger.info("Found following versions in json {}", versions);
		} catch (IOException e) {
			logger.error("URLStatusError while getting Versions from Json api {}", url, e);
		}
		return versions;
	}

	protected abstract String doGetVersionUrl();

	protected abstract Class<J> getJsonObjectType();

	protected abstract void collectVersionsFromJson(J jsonItem, Collection<String> versions);
}
