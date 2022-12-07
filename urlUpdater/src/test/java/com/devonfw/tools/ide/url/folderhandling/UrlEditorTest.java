package com.devonfw.tools.ide.url.folderhandling;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class UrlEditorTest {

	@Test
	public void test() throws IOException {
		Path repoPath = Paths
				.get("C:\\projects\\Issue941newMirrors\\workspaces\\newMirrors281122\\ide\\urlUpdater\\src\\test\\resources\\urlEditorTest");


		UrlEditor urlEditorObject = new UrlEditor(repoPath.toString());

		String tool="docker";
		urlEditorObject.createFolder(tool);
		File ft = new File(repoPath + File.separator + tool );
		assertTrue(ft.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getName().equals(tool));

		String edition="rancher";
		urlEditorObject.createFolder(tool, edition);
		File fte = new File(repoPath + File.separator + tool + File.separator + edition);
		assertTrue(fte.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getName().equals(edition));

		String version="1.6.2";
		urlEditorObject.createFolder(tool,edition,version);
		File ftev = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version);
		assertTrue(ftev.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getName().equals(version));


		urlEditorObject.createFile(tool,edition,version);
		File ftevUrls = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version + File.separator + "urls");
		assertTrue(ftevUrls.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild("urls").getName().equals("urls"));

		String os="win";
		urlEditorObject.createFile(tool,edition,version, os);
		File ftevo = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version + File.separator + os + ".urls");
		assertTrue(ftevo.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild(os + ".urls").getName().equals(os + ".urls"));


		String arch="x86";
		urlEditorObject.createFile(tool,edition,version, os, arch);
		File ftevoa = new File(repoPath + File.separator + tool + File.separator + edition + File.separator + version + File.separator + os + "_" + arch + ".urls");
		assertTrue(ftevoa.exists());
		assertTrue(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild(os + "_" + arch + ".urls").getName().equals(os + "_" + arch + ".urls"));


		assertTrue(urlEditorObject.getFolder(tool).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool)));
		assertTrue(urlEditorObject.getFolder(tool, edition).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition)));
		assertTrue(urlEditorObject.getFolder(tool, edition, version).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version)));
		assertTrue(urlEditorObject.getFile(tool, edition, version).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild("urls")));
		assertTrue(urlEditorObject.getFile(tool, edition, version, os).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild(os + ".urls")));
		assertTrue(urlEditorObject.getFile(tool, edition, version, os, arch).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild(os + "_" + arch + ".urls")));
		assertTrue(urlEditorObject.getFile(tool, edition, version, os, arch).equals(urlEditorObject.getUrlRepositoryObject().getChild(tool).getChild(edition).getChild(version).getChild(os + "_" + arch + ".urls")));

		UrlFile fileNamedUrls = urlEditorObject.getFile(tool, edition, version);
		UrlFile fileForOs = urlEditorObject.getFile(tool, edition, version, os);
		UrlFile fileForOsArch = urlEditorObject.getFile(tool, edition, version, os, arch);

		
		String urlToAdd = "url/without/os/Or/arch2";
		urlEditorObject.addUrls("url/without/os/Or/arch2", fileNamedUrls);
		assertTrue(fileNamedUrls.getObjectsList().contains(urlToAdd));
		ArrayList<String> fileContentAfterAdd = fileNamedUrls.debugLoadFileContent();
		assertTrue(fileNamedUrls.getObjectsList().equals(fileContentAfterAdd));

		urlEditorObject.removeUrls("url/without/os/Or/arch2", fileNamedUrls);
		ArrayList<String> fileContentAfterRemoval = fileNamedUrls.debugLoadFileContent();
		assertTrue(fileNamedUrls.getObjectsList().equals(fileContentAfterRemoval));

		String url1 = "/url/1";
		String url2 = "/url/2";
		String url3 = "/url/3";
		ArrayList<String> urlListe = new ArrayList<>();
		urlListe.add(url1);
		urlListe.add(url2);
		urlListe.add(url3);
		urlEditorObject.addUrls(urlListe, fileNamedUrls);
		ArrayList<String> fileContentAfterListAdd = fileNamedUrls.debugLoadFileContent();
		assertTrue(fileNamedUrls.getObjectsList().equals(fileContentAfterListAdd));



	}

}
