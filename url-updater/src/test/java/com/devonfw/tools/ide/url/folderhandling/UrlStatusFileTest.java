//package com.devonfw.tools.ide.url.folderhandling;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.jupiter.api.Test;
//
//import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlJsonCompleteDataBlock;
//import com.devonfw.tools.ide.url.folderhandling.jsonfile.JsonDataBlockForSpecificUrl;
///**
// *
// * Mainly creates a JSON-File including a manual entry and one url data block (as a first draft),
// * with hashes based on the urls inside the file "mac_x64.urls".
// * For now there is one hash list per urls-file.
// * Please keep in mind, that the variable pathToRepo has to be adjusted to your environment
// * and to include the above mentioned files with (at least some dummy-)urls for testing.
// *
// *
// */
//class UrlStatusFileTest {
//
//  @Test
//  void test() {
//
//    Path pathToRepo = Paths.get(
//        "C:\\urlsRepo");
//    UrlRepository urlRepoObjBeforeLoad = new UrlRepository(pathToRepo);
//    UrlRepository urlRepoObj = urlRepoObjBeforeLoad.load(pathToRepo);
//    UrlTool dockerAsToolObj = urlRepoObj.getChild("docker");
//    UrlEdition rancherAsEditionObj = urlRepoObj.getChild("docker").getChild("rancher");
//    UrlVersion rancherV162 = rancherAsEditionObj.getChild("1.6.2");
//
//    UrlStatusFile statusFile = new UrlStatusFile(rancherV162);
//    UrlDownloadFile macDownloadFile = new UrlDownloadFile(rancherV162, "mac_x64.urls");
//    System.out.println(statusFile.getPath().toString());
//    macDownloadFile.doLoad();
//    System.out.println(macDownloadFile.getUrlCount());
//    macDownloadFile.getUrls().forEach(u -> System.out.println(u));
//
//    Set<Double> macHashSet = macDownloadFile.generateUrlHashes();
//    JsonDataBlockForSpecificUrl dataBlockForMac = new JsonDataBlockForSpecificUrl(macHashSet);
//    boolean manual = false;
//
//    Set<JsonDataBlockForSpecificUrl> dataBlocks = new HashSet<>();
//    dataBlocks.add(dataBlockForMac);
//    UrlJsonCompleteDataBlock jsonData = new UrlJsonCompleteDataBlock(manual, dataBlocks);
//    jsonData.addSingleDataBlock(dataBlockForMac);
//    statusFile.setJsonFileData(jsonData);
//    statusFile.doSave();
//  }
//
//}
