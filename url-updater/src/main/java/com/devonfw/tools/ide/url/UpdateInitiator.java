package com.devonfw.tools.ide.url;

import com.devonfw.tools.ide.url.updater.UpdateManager;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;
import com.devonfw.tools.ide.url.updater.java.JavaCrawler;


import java.nio.file.Path;

public class UpdateInitiator {

    public static void main(String[] args) {
        String pathToRepo ="./target/test/UrlRepository";
        UpdateManager updateManager = new UpdateManager(Path.of(pathToRepo));
        updateManager.updateAll();

    }
}