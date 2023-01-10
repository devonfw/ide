package com.devonfw.tools.ide.url.Updater.java;

import com.devonfw.tools.ide.url.Updater.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JavaJsonObject implements JsonObject {
    private final List<JavaJsonVersion> versions = new ArrayList<>();

    public List<JavaJsonVersion> getVersions () {
        return versions;
    }
}

