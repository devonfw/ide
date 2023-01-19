package com.devonfw.tools.ide.url;

import com.devonfw.tools.ide.url.updater.UpdateManager;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;


import java.nio.file.Path;

public class UpdateInitiator {

    public static void main(String[] args) {
        String pathToRepo ="../../ide-urls";
        UrlRepository urlRepository = new UrlRepository(Path.of(pathToRepo));
        UpdateManager updateManager = new UpdateManager(urlRepository);
        updateManager.updateAll();





    }
}