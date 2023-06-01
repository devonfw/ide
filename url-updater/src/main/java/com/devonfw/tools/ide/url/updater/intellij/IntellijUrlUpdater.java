package com.devonfw.tools.ide.url.updater.intellij;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.common.SystemArchitecture;
import com.devonfw.tools.ide.json.mapping.JsonMapping;
import com.devonfw.tools.ide.url.model.folder.UrlEdition;
import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.devonfw.tools.ide.url.model.folder.UrlTool;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.url.updater.JsonUrlUpdater;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.devonfw.tools.ide.url.updater.WebsiteUrlUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  {@link IntellijUrlUpdater} base class for IntelliJ.
 */
public class IntellijUrlUpdater extends JsonUrlUpdater<IntellijJsonObject> {

  private final static String JSON_URL = "https://data.services.jetbrains.com/products?code=IIU%2CIIC&release.type=release&_=1682672979887";

  private static final ObjectMapper MAPPER = JsonMapping.create();

  private static final Logger logger = LoggerFactory.getLogger(IntellijUrlUpdater.class);

  private boolean editionSwitch = false;

  @Override
  public void update(UrlRepository urlRepository) {

    UrlTool tool = urlRepository.getOrCreateChild(getTool());
    try {
      URL jsonURL = new URL(JSON_URL);
      IntellijJsonObject[] jsonObj = MAPPER.readValue(jsonURL, IntellijJsonObject[].class);
      // Has 2 elements, 1. Ultimate Edition, 2. Community Edition
      for (int i = 0; i <= jsonObj.length - 1; i++) {
        if (i == 1)
          editionSwitch = true;
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        updateExistingVersions(edition);
        String toolWithEdition = getToolWithEdition();

        List<IntellijJsonReleases> releases = jsonObj[i].getReleases();
        for (IntellijJsonReleases r : releases) {
          String version = r.getVersion();
          Map<String, IntellijJsonDownloadsItem> downloads = r.getDownloads();
          if (edition.getChild(version) == null) {
            try {
              UrlVersion urlVersion = edition.getOrCreateChild(version);
              for (String os : downloads.keySet()) {
                if (os.equals("windowsZip")) {
                  addVersionEachOs(urlVersion, downloads, "windowsZip", OperatingSystem.WINDOWS,
                      SystemArchitecture.X64);
                } else if (os.equals("linux")) {
                  addVersionEachOs(urlVersion, downloads, "linux", OperatingSystem.LINUX, SystemArchitecture.X64);
                } else if (os.equals("mac")) {
                  addVersionEachOs(urlVersion, downloads, "mac", OperatingSystem.MAC, SystemArchitecture.X64);
                }
              }
              urlVersion.save();
            } catch (Exception e) {
              logger.error("For tool {} we failed to add version {}.", toolWithEdition, version, e);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Error while getting versions from JSON API " + JSON_URL, e);
    }
  }

  @Override
  protected void addVersion(UrlVersion urlVersion) {

    throw new IllegalStateException();
  }

  @Override
  protected String getEdition() {

    if (editionSwitch)
      return "ultimate";
    else
      return "community";
  }

  private void addVersionEachOs(UrlVersion url, Map<String, IntellijJsonDownloadsItem> downloads, String json_os,
      OperatingSystem os, SystemArchitecture systemArchitecture) {

    Map<String, Object> osValues = downloads.get(json_os).getOs_values();
    String link = osValues.get("link").toString();
    String checkSumLink = osValues.get("checksumLink").toString();
    String cs = getCheckSum(checkSumLink);
    doAddVersion(url, link, os, systemArchitecture, cs);

  }

  private String getCheckSum(String checksumLink) {

    if (checksumLink != null && !checksumLink.isEmpty()) {
      try {
        URL url = new URL(checksumLink);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String response = null;
          String line;

          if ((line = reader.readLine()) != null) {
            response = line.split(" ")[0];

            reader.close();
            connection.disconnect();

            return response;
          }
        }
      } catch (ProtocolException e) {
        throw new RuntimeException(e);
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }
    return null;
  }

  @Override
  protected String getTool() {

    return "intellij";
  }

  @Override
  protected String doGetVersionUrl() {

    return JSON_URL;
  }

  @Override
  protected Class<IntellijJsonObject> getJsonObjectType() {

    return IntellijJsonObject.class;
  }

  @Override
  protected void collectVersionsFromJson(IntellijJsonObject jsonItem, Collection<String> versions) {

  }
}