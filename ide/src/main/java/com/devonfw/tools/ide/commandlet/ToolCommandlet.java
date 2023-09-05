package com.devonfw.tools.ide.commandlet;

import java.util.List;

import com.devonfw.tools.ide.cli.functions.Functions;
import com.devonfw.tools.ide.environment.EnvironmentVariables;
import com.devonfw.tools.ide.environment.EnvironmentVariablesType;
import com.devonfw.tools.ide.version.VersionIdentifier;

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

  /**
   * @return the {@link EnvironmentVariables#getToolEdition(String) tool edition}.
   */
  protected String getEdition() {

    return context().getVariables().getToolEdition(getTool());
  }

  /**
   * @return the {@link EnvironmentVariables#getToolVersion(String) tool version}.
   */
  protected VersionIdentifier getVersion() {

    return context().getVariables().getToolVersion(getTool());
  }

  protected void setup() {

    Functions.setup("", this.DEVON_DOWNLOADS_DIR, getTool(), getVersion().toString(), "", "", "", "", "", "");
  }

  protected void listVersions() {

    List<VersionIdentifier> versions = context().getUrls().getSortedVersions(getTool(), getEdition());
    for (VersionIdentifier vi : versions) {
      context().info(vi.toString());
    }
  }

  /**
   * Sets the tool version in the environment variable configuration file.
   */
  protected void setVersion() {

    EnvironmentVariables variables = context().getVariables();
    EnvironmentVariables settingsVariables = variables.getByType(EnvironmentVariablesType.SETTINGS);
    String tool = getTool();
    String edition = getEdition();
    String name = EnvironmentVariables.getToolVersionVariable(tool);
    String value = this.setVersion.version;
    if ((value == null) || value.isBlank()) {
      // TODO throw exception instead and implement proper exception handling
      context().error("You have to specify the version you want to set.");
      return;
    }
    VersionIdentifier configuredVersion;
    if (value.equals("latest")) {
      configuredVersion = VersionIdentifier.LATEST;
    } else {
      configuredVersion = VersionIdentifier.of(value);
    }
    VersionIdentifier resolvedVersion = context().getUrls().getVersion(tool, edition, configuredVersion);
    if (configuredVersion.isPattern()) {
      context().debug("Resolved version {} to {} for tool {}/{}", configuredVersion, resolvedVersion, tool, edition);
    }
    settingsVariables.set(name, resolvedVersion.toString(), false);
    settingsVariables.save();
    context().info("{}={} has been set in {}", name, value, settingsVariables.getSource());
    EnvironmentVariables declaringVariables = variables.findVariable(name);
    if ((declaringVariables != null) && (declaringVariables != settingsVariables)) {
      context().warning(
          "The variable {} is overridden in {}. Please remove the overridden declaration in order to make the change affect.",
          name, declaringVariables.getSource());
    }
    // TODO we can now easily ask the user if he wants to do this automatically
    context().info("To install that version call the following command:");
    context().info("ide {} setup", tool);
  }

}
