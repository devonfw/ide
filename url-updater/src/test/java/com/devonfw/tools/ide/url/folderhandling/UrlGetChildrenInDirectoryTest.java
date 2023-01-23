//package com.devonfw.tools.ide.url.folderhandling;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
///**
// *
// *
// *
// */
//public class UrlGetChildrenInDirectoryTest extends Assertions {
//
//  @Test
//  public void test() throws IOException {
//
//    Path pathToRepo = Paths.get(
//        "C:\\projects\\Issue941newMirrors\\workspaces\\newMirrors281122\\ide\\url-updater\\src\\test\\resources\\UrlGetChildrenInDirectoryTest\\");
//    File f = new File(pathToRepo.toString());
//    f.mkdirs();
//
//    // Erzeugen der Beispielobjekte, welche die Ordner repräsentieren
//    // (wenn Ordnerstruktur bereits existiert, kann später eine bereits lokal implementierte
//    // Methode zum Einlesen der Ordnerstruktur, mit entsprechender Erzeugung der
//    // Objekte, genutzt werden).
//    UrlRepository UrlRepoObj = new UrlRepository(pathToRepo);
//    assertThat(UrlRepoObj.getPath()).isNotNull();
//    UrlTool UrlToolObj = new UrlTool(UrlRepoObj, "docker");
//    assertThat(UrlToolObj.getPath()).isNotNull();
//    UrlTool UrlToolObj2 = new UrlTool(UrlRepoObj, "vscode");
//    assertThat(UrlToolObj2.getPath()).isNotNull();
//
//    UrlEdition UrlEditionObj = new UrlEdition(UrlToolObj, "rancher");
//    assertThat(UrlEditionObj.getPath()).isNotNull();
//
//    UrlVersion UrlVersionObj = new UrlVersion(UrlEditionObj, "1.6.1");
//    assertThat(UrlVersionObj.getPath()).isNotNull();
//    UrlVersion UrlVersionObj2 = new UrlVersion(UrlEditionObj, "1.6.2");
//    assertThat(UrlVersionObj2.getPath()).isNotNull();
//
//    // Erzeugen der Ordnerstruktur, basierend auf den zuvor erzeugten Objekten.
//    UrlDownloadFile UrlFileObj = new UrlDownloadFile(UrlVersionObj, "linux.urls");
//    Files.createDirectories(UrlVersionObj2.getPath());
//    Files.createDirectories(UrlFileObj.getParent().getPath());
//    if (!Files.exists(UrlFileObj.getPath())) {
//      Files.createFile(UrlFileObj.getPath());
//    }
//
//    // Nutzen der neuen Methode, um Ordnerinhalt anzuzeigen. Dabei wurden
//    // entweder nur Ordner oder Dateien ausgegeben.
//    // Begründung: Jede JavaJsonVersion wird durch einen Ordner repräsentiert. Falls
//    // lock-Dateien oder andere Konfigurationsdateien sich in dem selben Ordner wie
//    // die Versionen befinden, so sollen diese entsprechend nicht ausgegeben werden.
//    // Andererseits wurde Rücksicht darauf genommen, dass in einem Versionsordner selbst
//    // lediglich Dateien enthalten sind (ggf. werden hier später lock-Dateien o.ä. ignoriert).
//    UrlRepoObj.getListOfAllChildren();
//    UrlToolObj.getListOfAllChildren();
//    UrlEditionObj.getListOfAllChildren();
//    UrlVersionObj.getListOfAllChildren();
//    // TO DO: Diese Methoden müssen wohl noch durch Asserts abgefangen werden.
//
//  }
//}
