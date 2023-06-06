package com.devonfw.tools.ide.integrationtests;

import com.devonfw.tools.ide.url.updater.intellij.IntellijUrlUpdater;

public class IntellijUrlUpdaterMock  extends IntellijUrlUpdater {
  private final static String TEST_BASE_URL = "http://localhost:8080";

  @Override
  protected String doGetVersionUrl() {
    return TEST_BASE_URL;
  }
}
