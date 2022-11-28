package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 */
public class UrlFile extends UrlHasParentArtifact<UrlVersion, UrlFile> {


	public UrlFile(UrlVersion parent, String name) {
		super(parent, name);

	}


}
