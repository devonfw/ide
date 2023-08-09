package com.devonfw.tools.ide.commandlet;

import java.util.Locale;

import com.devonfw.tools.ide.cli.functions.Functions;

import picocli.CommandLine;

/**
 * {@link Commandlet} for a tool integrated into the IDE.
 */
public abstract class ToolCommandlet extends Commandlet {
  private final String DEVON_DOWNLOADS_DIR = System.getProperty("user.home") + "/Downloads/devon/software/";

  protected abstract String getTool();

  protected abstract void startTool();

  @CommandLine.Option(names = "setup", description = "this option is used to setup the tool")
  private boolean setup;

  @CommandLine.Option(names = { "version", "versions",
  "v" }, description = "this option is used handle versions for the tool and needs to be followed by other options")
  private boolean version;

  @CommandLine.Option(names = { "list",
  "ls" }, description = "this option is used in combination with the <versions> option to list the versions")
  private boolean list;

  @CommandLine.ArgGroup(exclusive = false)
  SetVersion setVersion;

  static class SetVersion {
    @CommandLine.Option(names = { "set",
    "sv" }, description = "this option is used in combination with the <versions> option to set a certain version")
    boolean setVersion;

    @CommandLine.Option(names = { "-v", "--version" }, required = true, description = "the version to set")
    String version;
  }

  @Override
  public void run() {

    if (this.setup) {
      setup();
    } else if (this.version && this.list) {
      listVersions();
    } else if (this.version && this.setVersion.setVersion) {
      if (this.setVersion.version.isEmpty()) {
        context().error("Please provide a correct version.");
        // TODO avoid calls to System.exit except in main method
        System.exit(1);
      }
      setVersion();
    } else {
      startTool();
    }
  }

  protected String getEdition() {

    String tool = getTool();
    String editionVariable = tool.toUpperCase(Locale.ROOT) + "_EDITION";
    String editionValue = context().env().getVariables().get(editionVariable);
    if (editionValue == null) {
      editionValue = tool;
    }
    return editionValue;
  }

  protected String getVersion() {

    String versionVariable = getTool().toUpperCase(Locale.ROOT) + "_VERSION";
    String versionValue = context().env().getVariables().get(versionVariable);
    return versionValue;
  }

  protected void setup() {

    Functions.setup("", this.DEVON_DOWNLOADS_DIR, getTool(), getVersion(), "", "", "", "", "", "");
  }

  protected void listVersions() {

    Functions.listVersions(getTool(), getEdition());
  }

  protected void setVersion() {

    Functions.setSoftwareVersion(getTool(), this.setVersion.version);
  }

}
