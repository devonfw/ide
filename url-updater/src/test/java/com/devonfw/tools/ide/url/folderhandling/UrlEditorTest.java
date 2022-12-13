package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlEditorTest extends Assertions {

  @Test
  public void test() throws IOException {

    Path repoPath = Paths.get(
        "C:\\projects\\Issue941newMirrors\\workspaces\\newMirrors281122\\ide\\url-updater\\src\\test\\resources\\urlEditorTest");

    UrlEditor urlEditorObject = new UrlEditor(repoPath.toString());
    String tool = "docker";
    urlEditorObject.createFolder(tool);
    File ft = new File(repoPath + File.separator + tool);
    assertThat(ft).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getName()).isEqualTo(tool);

    String edition = "rancher";
    urlEditorObject.createFolder(tool, edition);
    File fte = new File(repoPath + File.separator + tool + File.separator + edition);
    assertThat(fte).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getName()).isEqualTo(edition);

    String version = "1.6.2";
    urlEditorObject.createFolder(tool, edition, version);
    File ftev = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version);
    assertThat(ftev).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getName())
        .isEqualTo(version);

    urlEditorObject.createFile(tool, edition, version);
    File ftevUrls = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version
        + File.separator + "urls");
    assertThat(ftevUrls).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version)
        .getChild("urls").getName()).isEqualTo("urls");

    String os = "win";
    urlEditorObject.createFile(tool, edition, version, os);
    File ftevo = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version
        + File.separator + os + ".urls");
    assertThat(ftevo).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version)
        .getChild(os + ".urls").getName()).isEqualTo(os + ".urls");

    String arch = "x64";
    urlEditorObject.createFile(tool, edition, version, os, arch);
    File ftevoa = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version
        + File.separator + os + "_" + arch + ".urls");
    assertThat(ftevoa).exists();
    assertThat(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version)
        .getChild(os + "_" + arch + ".urls").getName()).isEqualTo(os + "_" + arch + ".urls");

    assertThat(urlEditorObject.getFolder(tool).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool)));
    assertThat(urlEditorObject.getFolder(tool, edition)
        .equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition)));
    assertThat(urlEditorObject.getFolder(tool, edition, version)
        .equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version)));
    assertThat(urlEditorObject.getFile(tool, edition, version).equals(
        urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild("urls")));
    assertThat(urlEditorObject.getFile(tool, edition, version, os).equals(urlEditorObject.getUrlRepositoryObject()
        .getChild(tool).getChild(edition).getChild(version).getChild(os + ".urls")));
    assertThat(urlEditorObject.getFile(tool, edition, version, os, arch).equals(urlEditorObject.getUrlRepositoryObject()
        .getChild(tool).getChild(edition).getChild(version).getChild(os + "_" + arch + ".urls")));
    assertThat(urlEditorObject.getFile(tool, edition, version, os, arch).equals(urlEditorObject.getUrlRepositoryObject()
        .getChild(tool).getChild(edition).getChild(version).getChild(os + "_" + arch + ".urls")));

    UrlFile fileNamedUrls = urlEditorObject.getFile(tool, edition, version);
    UrlFile fileForOs = urlEditorObject.getFile(tool, edition, version, os);
    UrlFile fileForOsArch = urlEditorObject.getFile(tool, edition, version, os, arch);

    String urlToAdd = "url/without/os/Or/arch2";
    urlEditorObject.addUrls("url/without/os/Or/arch2", fileNamedUrls);
    assertThat(fileNamedUrls.getObjectsSet()).contains(urlToAdd);
    Set<String> fileContentAfterAdd = fileNamedUrls.debugLoadFileContent();
    assertThat(fileNamedUrls.getObjectsSet()).isEqualTo(fileContentAfterAdd);

    urlEditorObject.removeUrls("url/without/os/Or/arch2", fileNamedUrls);
    Set<String> fileContentAfterRemoval = fileNamedUrls.debugLoadFileContent();
    assertThat(fileNamedUrls.getObjectsSet()).isEqualTo(fileContentAfterRemoval);

    String url1 = "/url/1";
    String url2 = "/url/2";
    String url3 = "/url/3";
    Set<String> urlListe = new HashSet<>();
    urlListe.add(url1);
    urlListe.add(url2);
    urlListe.add(url3);
    urlEditorObject.addUrls(urlListe, fileNamedUrls);
    Set<String> fileContentAfterListAdd = fileNamedUrls.debugLoadFileContent();
    assertThat(fileNamedUrls.getObjectsSet()).isEqualTo(fileContentAfterListAdd);

  }

}
