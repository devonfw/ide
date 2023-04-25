package com.devonfw.tools.ide.url.updater.intellij;

import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.devonfw.tools.ide.url.updater.WebsiteCrawler;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class IntelliJCrawler extends WebsiteCrawler {
  @Override
  protected Set<String> getVersions() {

    String url = getVersionUrl();
    WebDriver driver = setupSelenium();
    try {
      driver.get(url);
      String html = driver.getPageSource();
      Thread.sleep(5000);
      driver.quit();
      return doGetRegexMatchesAsList(html);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to retrieve response body from url: " + url, e);
    }
  }

  @Override
  protected String getVersionUrl() {

    return "https://www.jetbrains.com/idea/download/other.html";
  }

  @Override
  protected Pattern getVersionPattern() {

    return Pattern.compile("(\\d{4}\\.\\d\\.\\d)");
  }

  private WebDriver setupSelenium() {

    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-browser-side-navigation");
    options.addArguments("--disable-gpu");
    options.addArguments("--no-sandbox");
    options.addArguments("disable-infobars");
    options.setHeadless(true);
    return new ChromeDriver(options);
  }

  @Override
  protected String getToolName() {

    return "intellij";
  }

  @Override
  protected String mapVersion(String version) {

    return version.replace("-", ".");
  }
}
