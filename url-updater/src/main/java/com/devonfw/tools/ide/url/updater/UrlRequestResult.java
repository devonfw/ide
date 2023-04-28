package com.devonfw.tools.ide.url.updater;

/**
 * Simple data object for the response status of an HTTP request.
 */
public class UrlRequestResult {

  private final int statusCode;

  private final String url;

  /**
   * The constructor.
   *
   * @param statusCode the {@link #getStatusCode() HTTP status code}.
   * @param url the requested URL.
   */
  public UrlRequestResult(int statusCode, String url) {

    this.statusCode = statusCode;
    this.url = url;
  }

  /**
   * @return {@code true} if {@link #getStatusCode() HTTP status code} is success, {@code false} otherwise.
   */
  public boolean isSuccess() {

    return this.statusCode == 200;
  }

  /**
   * @return {@code true} if not {@link #isSuccess() successful}, {@code false} otherwise.
   */
  public boolean isFailure() {

    return !isSuccess();
  }

  /**
   * @return the HTTP status code.
   */
  public int getStatusCode() {

    return this.statusCode;
  }

  /**
   * @return the originally requested URL.
   */
  public String getUrl() {

    return this.url;
  }
}