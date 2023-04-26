package com.devonfw.tools.ide.url.updater;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.tools.ide.common.JsonObject;
import com.devonfw.tools.ide.json.mapping.JsonMapping;
import com.devonfw.tools.ide.url.model.folder.UrlEdition;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link AbstractUrlUpdater} that retrieves the {@link UrlVersion versions} of a {@link UrlEdition tool edition} from a
 * HTTP response with JSON body.
 *
 * @param <J> type of the {@link JsonObject}.
 */
public abstract class JsonUrlUpdater<J extends JsonObject> extends AbstractUrlUpdater {

  private final Logger logger = LoggerFactory.getLogger(JsonUrlUpdater.class.getName());

  private static final ObjectMapper MAPPER = JsonMapping.create();

  @Override
  protected Set<String> getVersions() {

    String url = doGetVersionUrl();
    Set<String> versions = new HashSet<>();
    try {
      String response = doGetResponseBodyAsString(url);
      J jsonObject = MAPPER.readValue(response, getJsonObjectType());
      collectVersionsFromJson(jsonObject, versions);
      this.logger.info("Found following versions for tool {} in JSON {}", getToolWithEdition(), versions);
    } catch (Exception e) {
      throw new IllegalStateException("Error while getting versions from JSON API " + url, e);
    }
    return versions;
  }

  protected abstract String doGetVersionUrl();

  protected abstract Class<J> getJsonObjectType();

  protected abstract void collectVersionsFromJson(J jsonItem, Collection<String> versions);
}
