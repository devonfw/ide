package com.devonfw.tools.ide.url.folderhandling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

public class UrlFileTest {

	@Test
	public void test() throws IOException {
		Path pathToRepo = Paths
				.get("C:\\projects\\Mirrors-IDE\\workspaces\\ide-urls\\url-updater\\src\\test\\resources\\urlsRepo");
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

		UrlFile UrlFileObj = new UrlFile(UrlVersionObj, "linux.urls");


		UrlFileObj.addToObjectsList("url/3");
		UrlFileObj.addToObjectsList("url/2");
		UrlFileObj.addToObjectsList("url/1");
		UrlFileObj.addToObjectsList("url/0");

		ArrayList<String> currentList = UrlFileObj.getObjectsList();

		UrlFileObj.saveListFromObjectIntoFile();

		List<String> allLines = Files.readAllLines(UrlFileObj.getPath());


		int lengthOfList = currentList.size();
		for (int i=0; i<lengthOfList; i++) {
			assertThat(currentList.get(i)).isEqualTo(allLines.get(i));
		}

		UrlFileObj.removeLineFromObjectsList("url/0");

		currentList.forEach(line -> System.out.println(line));

		UrlFileObj.saveListFromObjectIntoFile();

		allLines = Files.readAllLines(UrlFileObj.getPath());

		lengthOfList = currentList.size();
		for (int i=0; i<lengthOfList; i++) {
			assertThat(currentList.get(i)).isEqualTo(allLines.get(i));
		}

		assertThat(UrlFileObj).isInstanceOf(UrlFile.class);




	}

}
