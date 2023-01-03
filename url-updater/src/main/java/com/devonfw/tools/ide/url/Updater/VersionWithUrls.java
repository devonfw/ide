package com.devonfw.tools.ide.url.Updater;

import java.util.Map;
import java.util.Set;

public class VersionWithUrls {
    private String version;
    private Map<String, Set<String>> urlsByOsAndArch;

    public VersionWithUrls(String version, Map<String, Set<String>> urlsByOsAndArch) {
        this.version = version;
        this.urlsByOsAndArch = urlsByOsAndArch;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, Set<String>> getUrlsByOsAndArch() {
        return urlsByOsAndArch;
    }
}