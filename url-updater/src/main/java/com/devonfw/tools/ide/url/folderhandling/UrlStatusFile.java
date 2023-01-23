package com.devonfw.tools.ide.url.folderhandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.NoSuchElementException;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.StatusJson;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.URLStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * {@link UrlFile} for the "status.json" file.
 */
public class UrlStatusFile extends AbstractUrlFile {

	/** Constant {@link UrlStatusFile#getName() filename}. */
	public static final String STATUS_JSON = "status.json";
	private StatusJson jsonFileData = new StatusJson();

	private static final ObjectMapper MAPPER;
	static {
		MAPPER = new ObjectMapper();
		MAPPER.registerModule(new JavaTimeModule());
		MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
	}

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

	public void setJsonFileData(StatusJson jsonFileData) {
		this.jsonFileData = jsonFileData;
	}


	@Override
	public void doLoad() {
		Path path = getPath();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			this.jsonFileData = MAPPER.readValue(reader, StatusJson.class);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to load " + path, e);
		}
	}

	@Override
	public void doSave() {

		try (BufferedWriter writer = Files.newBufferedWriter(getPath(), StandardOpenOption.CREATE)) {
			MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			MAPPER.writeValue(writer, this.jsonFileData);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to save file " + getPath(), e);
		}

	}

}

//	@Override
//	public void doLoad() {
//
//		this.urls.clear();
//		Path path = getPath();
//		try (BufferedReader br = Files.newBufferedReader(path)) {
//			String line;
//			do {
//				line = br.readLine();
//				if (line != null) {
//					this.urls.add(line.trim());
//				}
//			} while (line != null);
//		} catch (IOException e) {
//			throw new IllegalStateException("Failed to load file " + path, e);
//		}
//	}
//
//	@Override
//	public void doSave() {
//
//		Path path = getPath();
//		try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
//				StandardOpenOption.WRITE,StandardOpenOption.CREATE)) {
//			for (String line : this.urls) {
//				bw.write(line + "\n");
//			}
//		} catch (IOException e) {
//			throw new IllegalStateException("Failed to save file " + path, e);
//		}
//	}