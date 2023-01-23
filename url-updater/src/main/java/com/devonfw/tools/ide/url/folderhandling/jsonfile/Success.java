package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.time.Instant;

public class Success {
    private String timestamp;

    public Success() {
        this.timestamp = Instant.now().toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}