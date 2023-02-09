package com.devonfw.tools.ide.url;

import com.devonfw.tools.ide.url.updater.UpdateManager;

import java.nio.file.Path;

public class UpdateInitiator {

    public static void main(String[] args) {
        String pathToRepo = args[0]
        UpdateManager updateManager = new UpdateManager(Path.of(pathToRepo));
        updateManager.updateAll();
    }
}