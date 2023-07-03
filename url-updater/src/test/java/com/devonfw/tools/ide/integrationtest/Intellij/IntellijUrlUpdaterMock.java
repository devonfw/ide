package com.devonfw.tools.ide.integrationtest.Intellij;

import com.devonfw.tools.ide.url.updater.intellij.IntellijUrlUpdater;

public class IntellijUrlUpdaterMock extends IntellijUrlUpdater {
  private final static String TEST_BASE_URL = "http://localhost:8080";

  @Override
  protected String getVersionBaseUrl() {

    return TEST_BASE_URL;
  }
}
