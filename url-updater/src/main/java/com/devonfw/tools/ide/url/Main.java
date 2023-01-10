package com.devonfw.tools.ide.url;

//import com.devonfw.tools.ide.url.Updater.UpdateManager;
import com.devonfw.tools.ide.url.Updater.java.JavaCrawler;
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
        JavaCrawler cobigenCrawler = new JavaCrawler();
        cobigenCrawler.update(urlRepository);





    }
}