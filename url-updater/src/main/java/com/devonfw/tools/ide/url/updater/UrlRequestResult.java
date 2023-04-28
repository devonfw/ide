package com.devonfw.tools.ide.url.updater;

public class UrlRequestResult {

  private final int statusCode;

  private final String url;

  public UrlRequestResult(int statusCode, String url) {

    this.statusCode = statusCode;
    this.url = url;
  }

  public boolean isSuccess() {

    return this.statusCode == 200;
  }

  public boolean isFailure() {

    return !isSuccess();
  }

  public int getStatusCode() {

    return this.statusCode;
  }

  public String getUrl() {

    return this.url;
  }
}