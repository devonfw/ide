package com.devonfw.tools.ide.commandlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.devonfw.tools.ide.cli.CliException;
import com.devonfw.tools.ide.common.OperatingSystem;
import com.devonfw.tools.ide.common.SystemInfo;
import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.environment.EnvironmentVariables;
import com.devonfw.tools.ide.environment.EnvironmentVariablesType;
import com.devonfw.tools.ide.io.FileAccess;
import com.devonfw.tools.ide.io.TarCompression;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.process.ProcessContext;
import com.devonfw.tools.ide.url.model.file.UrlChecksum;
import com.devonfw.tools.ide.url.model.file.UrlDownloadFile;
import com.devonfw.tools.ide.url.model.folder.UrlVersion;
import com.devonfw.tools.ide.util.FilenameUtil;
import com.devonfw.tools.ide.version.VersionIdentifier;

import picocli.CommandLine;

/**
 * {@link Commandlet} for a tool integrated into the IDE.
 */
public abstract class ToolCommandlet extends Commandlet {

  @CommandLine.Option(names = "setup", description = "this option is used to setup the tool")
  private boolean install;

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

  /**
   * The additional attributes added are: - arity: is set to 0..1 to make the parameter optional. - defaultValue: is set
   * to an empty string, indicating that if the parameter is not provided, it will default to an empty string. -
   * showDefaultValue: is used to display the default value in the usage help.
   **/
  @CommandLine.Parameters(description = "commandline arguments given to run this tool", arity = "0..n", defaultValue = "", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
  private String[] toolArs;

  /**
   * @return the name of the tool (e.g. "java", "mvn", "npm", "node").
   */
  protected abstract String getTool();

  @Override
  public void run() {

    if (this.install) {
      install(false);
    } else if (this.version && this.list) {
      listVersions();
    } else if (this.version && this.setVersion.setVersion) {
      if (this.setVersion.version.isEmpty()) {
        // TODO validation should already be handled by CLI framework (picocli)
        throw new CliException("Please provide a correct version.");
      }
      setVersion(this.setVersion.version);
    } else if (this.toolArs != null) {
      runTool(null, this.toolArs);
    }
  }

  /**
   * Ensures the tool is installed and then runs this tool with the given arguments.
   *
   * @param toolVersion the explicit version (pattern) to run.
   * @param args the commandline arguments to run the tool.
   */
  public void runTool(String toolVersion, String... args) {

    install(true);
    if (toolVersion != null) {
      throw new UnsupportedOperationException("Not yet implemented!");
    }
    Path toolPath = getToolPath();
    FileAccess fileAccess = context().getFileAccess();
    Path binPath = fileAccess.findFirst(toolPath, path -> path.getFileName().toString().equals("bin"), false);
    if (binPath == null) {
      binPath = toolPath;
    }
    Path binary = fileAccess.findFirst(binPath, this::isBinary, false);
    if (binary == null) {
      throw new IllegalStateException("Could not find executable binary for " + getTool() + " in " + binPath);
    }
    ProcessContext pc = context().newProcess().executable(binary).addArgs(args);
    pc.run();
  }

  private boolean isBinary(Path path) {

    String filename = path.getFileName().toString();
    String tool = getTool();
    if (filename.equals(tool)) {
      return true;
    } else if (filename.startsWith(tool)) {
      String suffix = filename.substring(tool.length());
      return context().getSystemInfo().getOs().isExecutable(suffix);
    }
    return false;
  }

  /**
   * @return the {@link EnvironmentVariables#getToolEdition(String) tool edition}.
   */
  protected String getEdition() {

    return context().getVariables().getToolEdition(getTool());
  }

  /**
   * @return the {@link #getTool() tool} with its {@link #getEdition() edition}. The edition will be omitted if same as
   *         tool.
   */
  protected final String getToolWithEdition() {

    String tool = getTool();
    String edition = getEdition();
    if (tool.equals(edition)) {
      return tool;
    }
    return tool + "/" + edition;
  }

  /**
   * @return the {@link Path} where the tool is located (installed).
   */
  protected Path getToolPath() {

    return context().getSoftwarePath().resolve(getTool());
  }

  /**
   * @return the {@link EnvironmentVariables#getToolVersion(String) tool version}.
   */
  protected VersionIdentifier getVersion() {

    return context().getVariables().getToolVersion(getTool());
  }

  /**
   * Method to be called for {@link #install(boolean)} from dependent {@link Commandlet}s.
   */
  public void install() {

    install(true);
  }

  /**
   * Performs the installation of the {@link #getTool() tool} managed by this {@link Commandlet}.
   *
   * @param silent - {@code true} if called recursively to suppress verbose logging, {@code false} otherwise.
   */
  protected void install(boolean silent) {

    doInstall(silent);
  }

  /**
   * @return {@code true} to extract (unpack) the downloaded binary file, {@code false} otherwise.
   */
  protected boolean isExtract() {

    return true;
  }

  /**
   * Installs or updates the managed {@link #getTool() tool}.
   *
   * @param silent - {@code true} if called recursively to suppress verbose logging, {@code false} otherwise.
   */
  protected void doInstall(boolean silent) {

    String tool = getTool();
    VersionIdentifier configuredVersion = getVersion();
    Path toolPath = getToolPath();
    Path toolVersionFile = toolPath.resolve(IdeContext.FILE_SOFTWARE_VERSION);
    String currentlyInstalledVersion = getInstalledToolVersion();
    String edition = getEdition();
    UrlVersion urlVersion = context().getUrls().getVersionFolder(tool, edition, configuredVersion);
    String resolvedVersion = urlVersion.getName();
    if (isInstalledVersion(resolvedVersion, currentlyInstalledVersion, silent)) {
      return;
    }
    context().info("Updating {} from version {} to version {}...", tool, currentlyInstalledVersion, resolvedVersion);
    FileAccess fileAccess = context().getFileAccess();
    SystemInfo sys = context().getSystemInfo();
    OperatingSystem os = sys.getOs();
    UrlDownloadFile urls = urlVersion.getMatchingUrls(os, sys.getArchitecture());
    List<String> urlList = new ArrayList<>(urls.getUrls());
    if (urlList.isEmpty()) {
      throw new IllegalStateException("Invalid ide-urls metadata with empty urls file at " + urls.getPath());
    }
    Collections.shuffle(urlList);
    for (String url : urlList) {
      String downloadFilename = createDownloadFilename(tool, edition, resolvedVersion, url, os, urls);
      Path target = context().getDownloadPath().resolve(downloadFilename);
      if (Files.exists(target)) {
        context().warning("Artifact already exists at {}\nTo force update please delete the file and run again.",
            target);
      } else {
        try {
          fileAccess.download(url, target);
        } catch (Exception e) {
          context().error(e, "Failed to download from " + url);
          continue;
        }
      }
      verifyChecksum(target, urls);
      if (Files.exists(toolPath)) {
        context().getFileAccess().backup(toolPath);
      }
      extract(target, toolPath);
      try {
        Files.writeString(toolVersionFile, resolvedVersion, StandardOpenOption.CREATE_NEW);
      } catch (IOException e) {
        throw new IllegalStateException("Failed to write version file " + toolVersionFile, e);
      }
      context().success("Successfully installed {}", tool);
      return;
    }
    throw new CliException("Download of " + getToolWithEdition() + " in version " + resolvedVersion
        + " failed after trying " + urlList.size() + " url(s).");
  }

  /**
   * @return the currently installed tool version or {@code null} if not found (tool not installed).
   */
  protected String getInstalledToolVersion() {

    Path toolPath = getToolPath();
    if (!Files.isDirectory(toolPath)) {
      context().debug("Tool {} not installed in {}", getTool(), toolPath);
      return null;
    }
    Path toolVersionFile = toolPath.resolve(IdeContext.FILE_SOFTWARE_VERSION);
    if (!Files.exists(toolVersionFile)) {
      Path legacyToolVersionFile = toolPath.resolve(IdeContext.FILE_LEGACY_SOFTWARE_VERSION);
      if (Files.exists(legacyToolVersionFile)) {
        toolVersionFile = legacyToolVersionFile;
      } else {
        context().warning("Tool {} is missing version file in {}", getTool(), toolVersionFile);
        return null;
      }
    }
    try {
      return Files.readString(toolVersionFile).trim();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read file " + toolVersionFile, e);
    }
  }

  private boolean isInstalledVersion(String expectedVersion, String installedVersion, boolean silent) {

    if (expectedVersion.equals(installedVersion)) {
      if (!silent) {
        context().info("Version {} of tool {} is already installed", installedVersion, getToolWithEdition());
      }
      return true;
    }
    return false;
  }

  /**
   * @param file the {@link Path} to the file to extract.
   * @param targetDir the {@link Path} to the directory where to extract (or copy) the file.
   */
  protected void extract(Path file, Path targetDir) {

    FileAccess fileAccess = context().getFileAccess();
    if (isExtract()) {
      String extension = FilenameUtil.getExtension(file.getFileName().toString());
      TarCompression tarCompression = TarCompression.of(extension);
      if (tarCompression != null) {
        fileAccess.untar(file, targetDir, tarCompression);
      } else if ("zip".equals(extension) || "jar".equals(extension)) {
        fileAccess.unzip(file, targetDir);
      } else if ("dmg".equals(extension)) {
        assert context().getSystemInfo().isMac();
        Path mountPath = context().getIdeHome().resolve(IdeContext.FOLDER_UPDATES).resolve(IdeContext.FOLDER_VOLUME);
        fileAccess.mkdirs(mountPath);
        ProcessContext pc = context().newProcess();
        pc.run("hdiutil", "attach", "-quiet", "-nobrowse", "-mountpoint", mountPath.toString(), file.toString());
        Path appPath = fileAccess.findFirst(mountPath, p -> p.getFileName().toString().endsWith(".app"), false);
        if (appPath == null) {
          throw new IllegalStateException("Failed to unpack DMG as no MacOS *.app was found in file " + file);
        }
        fileAccess.copy(appPath, targetDir);
        pc.run("hdiutil", "detach", "-force", mountPath.toString());
        // if [ -e "${target_dir}/Applications" ]
        // then
        // rm "${target_dir}/Applications"
        // fi
      } else if ("msi".equals(extension)) {
        context().newProcess().run("msiexec", "//a", file.toString(), "//qn", "TARGETDIR=" + targetDir.toString());
        // msiexec also creates a copy of the MSI
        Path msiCopy = targetDir.resolve(targetDir.getFileName());
        fileAccess.delete(msiCopy);
      } else if ("pkg".equals(extension)) {

        Path tmpDir = fileAccess.createTempDir("ide-pkg-");
        ProcessContext pc = context().newProcess();
        // we might also be able to use cpio from commons-compression instead of external xar...
        pc.run("xar", "-C", tmpDir.toString(), "-xf", file.toString());
        Path contentPath = fileAccess.findFirst(tmpDir, p -> p.getFileName().toString().equals("Payload"), true);
        fileAccess.untar(contentPath, targetDir, TarCompression.GZ);
        fileAccess.delete(tmpDir);
      } else {
        throw new IllegalStateException("Unknown archive format " + extension + ". Can not extract " + file);
      }
    } else {
      fileAccess.move(file, targetDir);
    }
  }

  /**
   * @param file the {@link Path} to the file to verify.
   * @param urls the {@link UrlDownloadFile}.
   */
  protected void verifyChecksum(Path file, UrlDownloadFile urls) {

    UrlVersion urlVersion = urls.getParent();
    UrlChecksum urlChecksum = urlVersion.getChecksum(urls.getName());
    if (urlChecksum == null) {
      IdeLogLevel level = IdeLogLevel.WARNING;
      if (urlVersion.getName().equals("latest")) {
        level = IdeLogLevel.DEBUG;
      }
      context().level(level).log("No checksum found for {}", urls);
    } else {
      String expectedChecksum = urlChecksum.getChecksum();
      String actualChecksum = context().getFileAccess().checksum(file);
      if (expectedChecksum.equals(actualChecksum)) {
        context().success("Checksum {} is correct.", actualChecksum);
      } else {
        throw new CliException("Downloaded file " + file + " has the wrong checksum!\n" //
            + "Expected " + expectedChecksum + "\n" //
            + "Download " + actualChecksum + "\n" //
            + "This could be a man-in-the-middle-attack, a download failure, or a release that has been updated afterwards.\n" //
            + "Please review carefully.\n" //
            + "Expected checksum can be found at " + urlChecksum.getPath() + ".\n" //
            + "Actual checksum (sha256sum) was computed from file " + file + "\n" //
            + "Installation was aborted for security reasons!");
      }
    }
  }

  private String createDownloadFilename(String tool, String edition, String resolvedVersion, String url,
      OperatingSystem os, UrlDownloadFile urls) {

    StringBuilder sb = new StringBuilder(32);
    sb.append(tool);
    sb.append("-");
    sb.append(resolvedVersion);
    if (!edition.equals(tool)) {
      sb.append("-");
      sb.append(edition);
    }
    String osArchUrls = urls.getName();
    if (osArchUrls.endsWith(".urls")) {
      sb.append("-");
      sb.append(osArchUrls.substring(0, osArchUrls.length() - 5)); // ".urls".length == 5
    }
    String extension = FilenameUtil.getExtension(url);
    if (extension == null) {
      // legacy fallback - should never happen
      if (os == OperatingSystem.LINUX) {
        extension = "tgz";
      } else {
        extension = "zip";
      }
      context().warning("Could not determine file extension from URL {} - guess was {} but may be incorrect.", url,
          extension);
    }
    sb.append(".");
    sb.append(extension);
    return sb.toString();
  }

  /**
   * List the available versions of this tool.
   */
  public void listVersions() {

    List<VersionIdentifier> versions = context().getUrls().getSortedVersions(getTool(), getEdition());
    for (VersionIdentifier vi : versions) {
      context().info(vi.toString());
    }
  }

  /**
   * Sets the tool version in the environment variable configuration file.
   *
   * @param version the version (pattern) to install.
   */
  public void setVersion(String version) {

    EnvironmentVariables variables = context().getVariables();
    EnvironmentVariables settingsVariables = variables.getByType(EnvironmentVariablesType.SETTINGS);
    String tool = getTool();
    String edition = getEdition();
    String name = EnvironmentVariables.getToolVersionVariable(tool);
    if ((version == null) || version.isBlank()) {
      // TODO throw exception instead and implement proper exception handling
      context().error("You have to specify the version you want to set.");
      return;
    }
    VersionIdentifier configuredVersion = VersionIdentifier.of(version);
    VersionIdentifier resolvedVersion = context().getUrls().getVersion(tool, edition, configuredVersion);
    if (configuredVersion.isPattern()) {
      context().debug("Resolved version {} to {} for tool {}/{}", configuredVersion, resolvedVersion, tool, edition);
    }
    settingsVariables.set(name, resolvedVersion.toString(), false);
    settingsVariables.save();
    context().info("{}={} has been set in {}", name, version, settingsVariables.getSource());
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
