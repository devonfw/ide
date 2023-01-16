package com.devonfw.tools.ide.url;

//import com.devonfw.tools.ide.url.Updater.UpdateManager;
import com.devonfw.tools.ide.url.Updater.gcviewer.GCViewerCrawler;
import com.devonfw.tools.ide.url.Updater.gh.GHCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Main {
    public static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        String pathToRepo ="I:\\UrlRepoTest";
        UrlRepository urlRepository = new UrlRepository(Path.of(pathToRepo));
       // UpdateManager updateManager = new UpdateManager(urlRepository);
       // updateManager.doUpdateAll();
        GHCrawler cobigenCrawler = new GHCrawler();
        cobigenCrawler.update(urlRepository);
        //Create a Thread for GC Viewer Crawler and call update
        Thread gcViewerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GCViewerCrawler gcViewerCrawler = new GCViewerCrawler();
                gcViewerCrawler.update(urlRepository);
            }
        });
        gcViewerThread.start();





    }
}