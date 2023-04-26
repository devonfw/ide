package com.devonfw.tools.ide.url.updater;

public class UrlRequestResult {
  private final boolean success;

  private final int httpStatusCode;

  private final String url;

  public UrlRequestResult(boolean success, int value, String url) {

    this.success = success;
    this.httpStatusCode = value;
    this.url = url;
  }

  public boolean isSuccess() {

    return this.success;
  }

  public boolean isFailure() {

    return !this.success;
  }

  public int getHttpStatusCode() {

    return this.httpStatusCode;
  }

  public String getUrl() {

    return this.url;
  }
}