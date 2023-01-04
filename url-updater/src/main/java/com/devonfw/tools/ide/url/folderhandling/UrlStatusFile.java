package com.devonfw.tools.ide.url.folderhandling;

import java.io.IOException;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;
import com.devonfw.tools.ide.url.folderhandling.jsonfile.UrlJsonCompleteDataBlock;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * {@link UrlFile} for the "status.json" file.
 */
public class UrlStatusFile extends AbstractUrlFile {

  /** Constant {@link UrlStatusFile#getName() filename}. */
  static final String STATUS_JSON = "status.json";
  private UrlJsonCompleteDataBlock jsonFileData;

  /**
   * The constructor.
   *
   * @param parent the {@link #getParent() parent folder}.
   */
  public UrlStatusFile(UrlVersion parent) {

    super(parent, STATUS_JSON);
  }

  public UrlJsonCompleteDataBlock getJsonFileData() {

    return jsonFileData;
  }

  public void setJsonFileData(UrlJsonCompleteDataBlock jsonFileData) {

    this.jsonFileData = jsonFileData;
  }

  @Override
  protected void doLoad() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      this.jsonFileData = mapper.readValue(this.getPath().toFile(), UrlJsonCompleteDataBlock.class);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  protected void doSave() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      mapper.writeValue(this.getPath().toFile(), this.jsonFileData);
    } catch (JsonGenerationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
