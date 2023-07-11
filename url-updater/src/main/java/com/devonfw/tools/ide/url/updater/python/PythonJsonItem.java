package com.devonfw.tools.ide.url.updater.python;

import com.devonfw.tools.ide.common.JsonObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class PythonJsonItem  {


    @JsonProperty("version")
    private String version;


    @JsonProperty("files")
    private List<PythonFiles> files;



    public String getVersion() {
        return this.version;
    }


    public List<PythonFiles> getFiles() {
        return files;
    }
}
