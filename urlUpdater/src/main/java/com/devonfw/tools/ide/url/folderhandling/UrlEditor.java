package com.devonfw.tools.ide.url.folderhandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class to enable easier usage of download-url-folders-structure by handling technicalities in the background.
 * Supposed to be used to interact with crawler class to get or remove download-urls.
 * Gives a method to create a folder or file, while objects are created in the background,
 * as well as a method for getting an object representing a folder or a file. 
 * Also a method for adding and removing urls is given.
 */
public class UrlEditor {

	protected Path repoPath;
	UrlRepository urlRepoObject;


	public UrlEditor(String PathToUrlRepo) {
		this.repoPath = Paths.get(PathToUrlRepo);

		this.urlRepoObject = new UrlRepository(repoPath);
		UrlReader<UrlArtifact> urlReaderObject = new UrlReader<UrlArtifact>();
		urlReaderObject.readFolderStructure(urlRepoObject);
	}

	//TO DO: Womöglich sollte der zweite Konstruktor entfernt werden,
	//		 da es nicht nötig ist das UrlRepository Objekt gesondert einzugeben.
	public UrlEditor(UrlRepository urlRepoObject) {
		// this.repoPath = Paths.get(PathToUrlRepo);
		this.repoPath = urlRepoObject.getPath();
		UrlReader<UrlArtifact> urlReaderObject = new UrlReader<UrlArtifact>();
		urlReaderObject.readFolderStructure(urlRepoObject);

	}


	public UrlRepository getUrlRepositoryObject() {
		return this.urlRepoObject;
	}

	public void createFolder(String Tool) {
		urlRepoObject.getOrCreateChild(Tool);
		File f = new File(repoPath + File.separator + Tool );
		f.mkdirs();
	}

	public void createFolder(String Tool, String Edition) {
		urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition);
		File f = new File(repoPath + File.separator + Tool + File.separator + Edition);
		f.mkdirs();
	}

	public void createFolder(String Tool, String Edition, String Version) {
		urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version);
		File f = new File(repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version);
		f.mkdirs();
	}



	public void createFile(String Tool, String Edition, String Version) {
		urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version).getOrCreateChild("urls");
		File f = new File(repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version + File.separator + "urls");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createFile(String Tool, String Edition, String Version, String os) {
		urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version).getOrCreateChild(os + ".urls");
		File f = new File(repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version + File.separator + os + ".urls");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createFile(String Tool, String Edition, String Version, String os, String arch) {
		urlRepoObject.getOrCreateChild(Tool).getOrCreateChild(Edition).getOrCreateChild(Version).getOrCreateChild(os + "_" + arch + ".urls");
		File f = new File(repoPath + File.separator + Tool + File.separator + Edition + File.separator + Version + File.separator + os + "_" + arch + ".urls");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//TO DO: *Doku zu Methodendefinition aktualisieren, nach schreiben der finalen Version.
	//		 *Ggf. in folgenden Methoden von getChild zu getOrCreateChild wechseln, oder klären wie
	//		  Fall zu behandeln in dem ein Kindobjekt garnicht existiert.
	/**
	 * The methods definition isn't final yet and just for temporary use to get the path to a specific folder
	 * in accordance with the url-repository structure faster. Please feel free to communicate that other types are needed.
	 * @param Tool
	 * @return
	 */
	public UrlTool getFolder(String Tool) {
		return urlRepoObject.getChild(Tool);

	}

	public UrlEdition getFolder(String Tool, String Edition) {
		return urlRepoObject.getChild(Tool).getChild(Edition);
	}

	public UrlVersion getFolder(String Tool, String Edition, String Version) {
		return urlRepoObject.getChild(Tool).getChild(Edition).getChild(Version);
	}

	/**
	 * Returns an UrlFile object that represents the file with urls called "urls". The
	 * method without os or arch parameters should only be used if there are no distinctions to be made regarding
	 * an users operating system or system architecture.
	 * @param Tool
	 * @param Edition
	 * @param Version
	 * @return
	 */
	public UrlFile getFile(String Tool, String Edition, String Version) {
		return urlRepoObject.getChild(Tool).getChild(Edition).getChild(Version).getChild("urls");
	}

	public UrlFile getFile(String Tool, String Edition, String Version, String os) {
		return urlRepoObject.getChild(Tool).getChild(Edition).getChild(Version).getChild(os + ".urls");
	}

	public UrlFile getFile(String Tool, String Edition, String Version, String os, String arch) {
		return urlRepoObject.getChild(Tool).getChild(Edition).getChild(Version).getChild(os + "_" + arch + ".urls");
	}



	public void addUrls(ArrayList<String> urlsList, UrlFile urlFile) {
		try {
			urlFile.addToObjectsList(urlsList);
			urlFile.saveListFromObjectIntoFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addUrls(String url, UrlFile urlFile) {
		try {
			urlFile.addToObjectsList(url);
			urlFile.saveListFromObjectIntoFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeUrls(ArrayList<String> urlsList, UrlFile urlFile) {
		try {
			urlFile.removeLineFromObjectsList(urlsList);
			urlFile.saveListFromObjectIntoFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeUrls(String url, UrlFile urlFile) {
		try {
			urlFile.removeLineFromObjectsList(url);
			urlFile.saveListFromObjectIntoFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
