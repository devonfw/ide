package com.devonfw.tools.ide.integrationtest;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.api.Assertions;

import com.devonfw.tools.ide.json.mapping.JsonMapping;
import com.devonfw.tools.ide.url.model.file.json.StatusJson;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractUrlUpdaterTest extends Assertions {

  protected StatusJson getStatusJson(Path versionsPath) {

    ObjectMapper MAPPER = JsonMapping.create();
    StatusJson statusJson = new StatusJson();
    Path statusJsonPath = versionsPath.resolve("status.json");

    if (Files.exists(statusJsonPath)) {
      try (BufferedReader reader = Files.newBufferedReader(statusJsonPath)) {
        statusJson = MAPPER.readValue(reader, StatusJson.class);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to load " + statusJsonPath, e);
      }
    }
    return statusJson;
  }
}
