package com.devonfw.tools.ide.url.folderhandling;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.devonfw.tools.ide.url.folderhandling.jsonfile.Architecture;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.Operatingsystem;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlJsonCompleteDataBlock;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlJsonDataBlockForSpecificUrlFile;

class UrlStatusFileTest {

  @Test
  void test() {

    Path pathToRepo = Paths.get(
        "C:\\projects\\Issue941newMirrors\\workspaces\\newMirrors281122\\ide\\url-updater\\target\\test\\AbstractUrlFolderTest\\urlsRepo");
    UrlRepository urlRepoObjBeforeLoad = new UrlRepository(pathToRepo);
    UrlRepository urlRepoObj = urlRepoObjBeforeLoad.load(pathToRepo);
    UrlTool dockerAsToolObj = urlRepoObj.getChild("docker");
    UrlEdition rancherAsEditionObj = urlRepoObj.getChild("docker").getChild("rancher");
    UrlVersion rancherV162 = rancherAsEditionObj.getChild("1.6.2");

    UrlStatusFile statusFile = new UrlStatusFile(rancherV162);
    UrlDownloadFile macDownloadFile = new UrlDownloadFile(rancherV162, "mac_x64.urls");
    statusFile.doLoad();
    macDownloadFile.doLoad();
    System.out.println(macDownloadFile.getUrlCount());
    UrlJsonCompleteDataBlock jsonData = statusFile.getJsonFileData();
    System.out.println("getManual: " + jsonData.getManual());
    jsonData.getDataBlocks().forEach(t -> System.out.println("getArchitecture: " + t.getArchitecture()));
    jsonData.getDataBlocks().forEach(t -> System.out.println("getOperatingSystem: " + t.getOperatingSystem()));
    jsonData.getDataBlocks().forEach(t -> System.out.println("getUrlHashes: " + t.getUrlHashes()));
    macDownloadFile.getUrls().forEach(u -> System.out.println(u));
    Set<Double> macHashSet = macDownloadFile.generateUrlHashes();
    UrlJsonDataBlockForSpecificUrlFile dataBlockForMac = new UrlJsonDataBlockForSpecificUrlFile(Operatingsystem.mac, Architecture.x64, macHashSet, macDownloadFile.getDateLastModified());
    jsonData.addSingleDataBlock(dataBlockForMac);
    statusFile.setJsonFileData(jsonData);
    statusFile.doSave();
  }

}
