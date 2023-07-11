package com.devonfw.tools.ide.url.updater.python;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PythonFiles {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("arch")
    private String arch;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("download_url")
    private String download_url;


    public String getFilename() {
        return filename;
    }

    public String getPlatform() {
        return platform;
    }

    public String getArch() {
        return arch;
    }

    public String getDownload_url() {
        return download_url;
    }
}
