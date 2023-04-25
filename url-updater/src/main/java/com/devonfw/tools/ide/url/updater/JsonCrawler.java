package com.devonfw.tools.ide.url.updater;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.tools.ide.json.mapping.JsonMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonCrawler<J extends JsonObject> extends AbstractCrawler {

  private final Logger logger = LoggerFactory.getLogger(JsonCrawler.class.getName());

  private static final ObjectMapper MAPPER = JsonMapping.create();

  @Override
  protected Set<String> getVersions() {

    String url = doGetVersionUrl();
    Set<String> versions = new HashSet<>();
    try {
      String response = doGetResponseBody(url);
      J jsonObject = MAPPER.readValue(response, getJsonObjectType());
      collectVersionsFromJson(jsonObject, versions);
      logger.info("Found following versions for tool {} in JSON {}", getToolWithEdition(), versions);
    } catch (Exception e) {
      logger.error("Error while getting versions from JSON API {}", url, e);
    }
    return versions;
  }

  protected abstract String doGetVersionUrl();

  protected abstract Class<J> getJsonObjectType();

  protected abstract void collectVersionsFromJson(J jsonItem, Collection<String> versions);
}
