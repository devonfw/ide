package com.devonfw.tools.ide.url.folderhandling.jsonfile;

import java.time.Instant;

public class Error {
    private String timestamp;
    private String message;

    public Error(String message){
        this.timestamp = Instant.now().toString();
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }
}