package com.devonfw.tools.ide.url.folderhandling;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.StatusJson;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.URLStatus;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.URLStatusError;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.URLStatusSuccess;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * {@link UrlFile} for the "status.json" file.
 */
public class UrlStatusFile extends AbstractUrlFile {

	/**
	 * Constant {@link UrlStatusFile#getName() filename}.
	 */
	public static final String STATUS_JSON = "status.json";
	private static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();
		MAPPER.registerModule(new JavaTimeModule());
		MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	private StatusJson jsonFileData = new StatusJson();

	/**
	 * The constructor.
	 *
	 * @param parent the {@link #getParent() parent folder}.
	 */
	public UrlStatusFile(UrlVersion parent) {
		super(parent, STATUS_JSON);
	}

	public StatusJson getJsonFileData() {
		return jsonFileData;
	}

	public void addSuccessUrlStatus(String urlHash) {
		URLStatus urlStatus = new URLStatus();
		urlStatus.setSuccess(new URLStatusSuccess());
		this.jsonFileData.addUrlStatus(urlHash, urlStatus);
		this.modified = true;
	}

	public void addErrorUrlStatus(String urlHash, String message) {
		URLStatus urlStatus = new URLStatus();
		urlStatus.setError(new URLStatusError(message));
		this.jsonFileData.addUrlStatus(urlHash, urlStatus);
		this.modified = true;
	}


	@Override
	public void doLoad() {
		Path path = getPath();
		if (!Files.exists(path)) {
			this.jsonFileData = new StatusJson();
			return;
		}
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			this.jsonFileData = MAPPER.readValue(reader, StatusJson.class);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to load " + path, e);
		}
	}

	@Override
	public void doSave() {
		try (BufferedWriter writer = Files.newBufferedWriter(getPath(), StandardOpenOption.CREATE)) {
			MAPPER.writeValue(writer, this.jsonFileData);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to save file " + getPath(), e);
		}
	}

}
