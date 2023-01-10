package com.devonfw.tools.ide.url.Updater.intellij;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.regex.Pattern;

public abstract class IntelliJCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d{4}\\.\\d\\.\\d)");

    }

    @Override
    protected List<String> doGetVersions(String url) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(url);
        }catch (Exception e){
            throw new IllegalStateException("Failed to retrieve response body from url: " + url, e);
        }
        String html = driver.getPageSource();
        System.out.println(html);
        driver.quit();
        return doGetRegexMatchesAsList(html);
    }

    @Override
    protected String getToolName() {
        return "IntelliJ";
    }


    @Override
    protected String getVersionUrl() {
        return "https://www.jetbrains.com/idea/download/other.html";
    }


    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.extensions.put(OSType.WINDOWS, "exe");
        mappings.extensions.put(OSType.LINUX, "tar.gz");
        mappings.extensions.put(OSType.MAC, "dmg");
        return mappings;
    }
}
