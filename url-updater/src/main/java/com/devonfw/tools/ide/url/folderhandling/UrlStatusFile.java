package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlJsonCompleteDataBlock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * {@link UrlFile} for the "status.json" file.
 */
public class UrlStatusFile extends AbstractUrlFile {

  /**
   * Constant {@link UrlStatusFile#getName() filename}.
   */
  static final String STATUS_JSON = "status.json";

  private UrlJsonCompleteDataBlock jsonFileData;

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

  /**
   * @return The variable {@link UrlJsonCompleteDataBlock jsonFileData}.
   * @see #doLoad()
   */
  public UrlJsonCompleteDataBlock getJsonFileData() {

    return this.jsonFileData;
  }

  /**
   * @param jsonFileData Json data to be set to be able to save this data into the represented Json file with
   *        {@link #doSave()}.
   */
  public void setJsonFileData(UrlJsonCompleteDataBlock jsonFileData) {

    this.jsonFileData = jsonFileData;
  }

  @Override
  public void doLoad() {

    try {
      this.jsonFileData = MAPPER.readValue(getPath().toFile(), UrlJsonCompleteDataBlock.class);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load file " + getPath(), e);
    }
  }

  @Override
  public void doSave() {

    try {
      MAPPER.writeValue(getPath().toFile(), this.jsonFileData);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write into file " + getPath(), e);
    }
  }

}
