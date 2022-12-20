package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlFileTest extends Assertions {

  @Test
  public void test() throws IOException {

    Path pathToRepo = Paths.get("src/test/resources/urlsRepo");
    UrlRepository urlRepoObj = new UrlRepository(pathToRepo);
    assertThat(urlRepoObj.getPath()).isNotNull();
    UrlTool urlToolObj = new UrlTool(urlRepoObj, "docker");
    assertThat(urlToolObj.getPath()).isNotNull();
    UrlTool UrlToolObj2 = new UrlTool(urlRepoObj, "vscode");
    assertThat(UrlToolObj2.getPath()).isNotNull();
    UrlEdition UrlEditionObj = new UrlEdition(urlToolObj, "rancher");
    assertThat(UrlEditionObj.getPath()).isNotNull();

    UrlVersion UrlVersionObj0 = new UrlVersion(UrlEditionObj, "1.6.1");
    assertThat(UrlVersionObj0.getPath()).isNotNull();
    UrlVersion UrlVersionObj = new UrlVersion(UrlEditionObj, "1.6.2");
    assertThat(UrlVersionObj.getPath()).isNotNull();

    UrlDownloadFile UrlFileObj = new UrlDownloadFile(UrlVersionObj, "linux.urls");

    UrlFileObj.addToObjectsList("url/3");
    UrlFileObj.addToObjectsList("url/2");
    UrlFileObj.addToObjectsList("url/1");
    UrlFileObj.addToObjectsList("url/0");

    Set<String> currentList = UrlFileObj.getObjectsSet();

    UrlFileObj.saveListFromObjectIntoFile();

    List<String> allLines = Files.readAllLines(UrlFileObj.getPath());

    assertThat(currentList).containsExactlyInAnyOrder(allLines.toArray(new String[allLines.size()]));

    UrlFileObj.removeLineFromObjectsList("url/0");

    UrlFileObj.saveListFromObjectIntoFile();

    allLines = Files.readAllLines(UrlFileObj.getPath());

    assertThat(currentList).containsExactlyInAnyOrder(allLines.toArray(new String[allLines.size()]));

    assertThat(UrlFileObj).isInstanceOf(UrlDownloadFile.class);

  }

}
