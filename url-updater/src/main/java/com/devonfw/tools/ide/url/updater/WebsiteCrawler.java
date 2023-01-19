package com.devonfw.tools.ide.url.updater;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public abstract class WebsiteCrawler extends AbstractCrawler {
    protected abstract Pattern getVersionPattern();

    protected abstract String getVersionUrl();

    private final Logger logger = Logger.getLogger(WebsiteCrawler.class.getName());



    @Override
    protected Set<String> getVersions() {
        return doGetRegexMatchesAsList(doGetResponseBody(getVersionUrl()));
    }

    protected Set<String> doGetRegexMatchesAsList(String htmlBody) {
        Set<String> versions = new HashSet<>();
        var matcher = getVersionPattern().matcher(htmlBody);
        while (matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            String match = result.group();
            versions.add(match);
        }
        logger.log(Level.INFO, "Found  versions : " + versions);
        return versions;
    }


}
