package com.devonfw.tools.ide.url.updater.python;

import com.devonfw.tools.ide.common.JsonObject;

import java.util.List;

public class PythonJsonObject implements JsonObject {


    private List <PythonJsonItem>Pythonreleases;

    public List<PythonJsonItem> getPythonreleases() {
        return Pythonreleases;
    }
}
