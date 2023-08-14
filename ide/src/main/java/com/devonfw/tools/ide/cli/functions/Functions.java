package com.devonfw.tools.ide.cli.functions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.devonfw.tools.ide.commandlet.EnvironmentCommand;

/**
 * @deprecated has to be redesigned from bash to object-oriented programming.
 */
@Deprecated
public class Functions {

  private static final String DEVON_IDE_HOME = EnvironmentCommand.get().get("DEVON_IDE_HOME");

  private static final String DEVON_SOFTWARE_DIR = DEVON_IDE_HOME + "\\software\\";

  public static void doLicenseAgreement() {

    File licenseAgreement = new File(DEVON_IDE_HOME + "/license-agreement.txt");
    if (!licenseAgreement.exists()) {
      System.out.println();
      logo();
      System.out.println();
      System.out.println("Welcome to devonfw-ide!");
      System.out.println(
          "This product and its 3rd party components is open-source software and can be used free (also commercially).");
      System.out.println(
          "However, before using it you need to read the license agreement with all involved licenses agreements.");
      System.out.println(
          "With confirming you take notice and agree that there is no warranty for using this product and its 3rd party components.");
      System.out.println("You are solely responsible for all risk implied by using this software.");
      System.out.println("You will be able to find it in one of the following locations:");
      System.out.println("https://github.com/devonfw/ide/blob/master/documentation/LICENSE.asciidoc");
      System.out.println("Also it is included in " + DEVON_IDE_HOME + "/devon-ide-doc.pdf");
      System.out.println();
      /*
       * if (!isBatch()) { doOpen("https://github.com/devonfw/ide/blob/master/documentation/LICENSE.asciidoc"); }
       */
      if (isBatch()) {
        System.err.println(
            "You need to accept these terms of use and all license agreements. Please rerun in interactive (non-batch) mode.");
      }
      boolean askToContinue = askToContinue("Do you accept these terms of use and all license agreements?", true);
      if (askToContinue) {
        try {
          licenseAgreement.createNewFile();
          String licenseText = "On "
              + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss"))
              + " you accepted the devonfw-ide License.\nhttps://github.com/devonfw/ide/blob/master/documentation/LICENSE.asciidoc";
          Files.write(Paths.get(licenseAgreement.getPath()), licenseText.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.exit(255);
      }
      System.out.println();
    }
  }

  private static void logo() {

    String[] logo = {
    "     ..........  ///                                                                                                   ",
    "    ..........  /////                  dd                                                      fffff                   ",
    "   ..........  ////////               ddd                                                     ffffff                   ",
    "  ..........  //////////              ddd                                                     ff                       ",
    " ..........    //////////        dddddddd   eeeeeee  vvv         vvv   oooo      nnnnnn     fffffff ww               ww",
    "..........      //////////     dddddddddd  eeeeeeeeee vvv       vvv oooooooooo  nnnnnnnnnn  fffffff  ww      w      ww ",
    "..........       //////////   dddd    ddd eeee    eee  vvv     vvv oooo    oooo nnn     nnn   ff      ww    www    ww  ",
    "..........      //////////    ddd     ddd eeeeeeeeeee   vvv   vvv  ooo      ooo nnn      nnn  ff       ww  wwwww  ww   ",
    " ..........    //////////     ddd     ddd eeeeeeeeeee    vvvvvvv   ooo      ooo nnn      nnn  ff        ww wwwww ww    ",
    "  ..........  //////////       ddd    ddd eeee            vvvvv     ooo    ooo  nnn      nnn  ff         wwww wwww     ",
    "   ........  //////////         dddddddd   eeeeeeeeee      vvv       oooooooo   nnn      nnn  ff          ww   ww      ",
    "     .....  //////////            ddddd      eeeeee         v          oooo     nnn      nnn  ff           w   w       ",
    "      ...  //////////                                                                                                  " };
    int len = System.getenv("COLUMNS") != null ? Integer.parseInt(System.getenv("COLUMNS")) : 120;
    if (len > 120) {
      len = 120;
    }
    for (String s : logo) {
      System.out.println(s.substring(0, Math.min(s.length(), len)));
    }
  }

  private static boolean isBatch() {

    return System.getenv("batch") != null;
  }

  private static boolean askToContinue(String question, boolean shouldReturn) {

    String defaultQuestion = "Do you want to continue? ";
    if (question == null || question.trim().isEmpty()) {
      question = defaultQuestion;
    }
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println(question);
      System.out.print("(yes/no): ");
      String answer = scanner.nextLine().trim();
      if (answer.equalsIgnoreCase("yes") || answer.isEmpty()) {
        return true;
      } else if (answer.equalsIgnoreCase("no")) {
        System.out.println("No...");
        if (shouldReturn) {
          return false;
        } else {
          System.exit(255);
        }
      } else {
        System.out.println("Please answer yes or no (or hit return for yes).");
      }
    }
  }

  public static void downloadAndExtract(String software, String version, String url, String tmpFile,
      String targetFolder, String downloadFilename) {

    try {
      URL downloadUrl = new URL(url);
      downloadFilename = (downloadFilename != null && !downloadFilename.isEmpty()) ? downloadFilename
          : downloadUrl.getPath().substring(downloadUrl.getPath().lastIndexOf('/') + 1);
      System.out.println("Trying to download " + downloadFilename + " from " + url);
      String target = targetFolder + downloadFilename;
      File targetFile = new File(target);
      if (targetFile.exists()) {
        System.out.println(
            "Artifact already exists at " + target + "\nTo force update please delete the file and run again.");
        return;
      }
      try (InputStream in = new BufferedInputStream(downloadUrl.openStream());
          FileOutputStream out = new FileOutputStream(tmpFile)) {
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = in.read(buffer, 0, 1024)) != -1) {
          out.write(buffer, 0, count);
        }
        if (!targetFile.getParentFile().exists()) {
          targetFile.getParentFile().mkdirs();
        }
        targetFile.delete();
        File tmpFileObj = new File(tmpFile);
        tmpFileObj.renameTo(targetFile);
        System.out.println("Download of " + downloadFilename + " from " + url + " succeeded.");
        extract(targetFolder + downloadFilename, DEVON_SOFTWARE_DIR + software);
        createOrUpdateVersionFile(software, version, DEVON_SOFTWARE_DIR + software);
      }
    } catch (IOException e) {
      System.err.println("Failed to download " + url + " with exception " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void setup(String url, String targetDir, String software, String version, String edition, String code,
      String os, String arch, String ext, String filename) {

    doLicenseAgreement();
    System.out.println("Setting up " + software + "...");
    File targetDirFile = new File(targetDir);
    if (!targetDirFile.exists()) {
      targetDirFile.mkdirs();
    }
    Path tmpFile;
    try {
      tmpFile = Files.createTempFile(software + "-" + version, "." + ext);
    } catch (IOException e) {
      throw new RuntimeException("Failed to create temporary file", e);
    }

    if (url.isEmpty() || url.equals("-")) {
      if (arch.isEmpty()) {
        arch = System.getProperty("os.arch");
      }
      if (os.isEmpty()) {
        if (isMacOs()) {
          os = "mac";
        } else if (isWindows()) {
          os = "windows";
        } else {
          os = System.getProperty("os.version");
        }
      }
    }
    if (edition.isEmpty() || edition == null) {
      edition = software;
    } else {
      saveEditionVariable(software, edition);
    }
    if (version.isEmpty() || version == null) {
      version = getLatestVersion(software, edition);
    }
    String urlDir = DEVON_IDE_HOME + "/urls/" + software + "/" + edition + "/" + version;
    Path urlDirPath = Paths.get(urlDir);
    String downloadUrlFile = null;
    try {
      try (Stream<Path> paths = Files.list(urlDirPath)) {
        String finalOs = os;
        downloadUrlFile = paths.filter(Files::isRegularFile)
            .filter(path -> path.getFileName().toString().contains(finalOs)).map(Path::toString).findFirst()
            .orElse(null);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      url = new String(Files.readAllBytes(Paths.get(downloadUrlFile)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    downloadAndExtract(software, version, url, String.valueOf(tmpFile), targetDir, filename);
    System.out.println("Installation of " + software + " succeeded.");
  }

  private static void createOrUpdateVersionFile(String software, String version, String softwareDir) {

    String versionFile = softwareDir + "\\.devon.software.version";
    String currentVersion = "";
    Path versionFilePath = Paths.get(versionFile);
    if (Files.exists(versionFilePath)) {
      try {
        currentVersion = new String(Files.readAllBytes(versionFilePath));
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (currentVersion.equals(version)) {
        System.out.println("Version " + version + " of " + software + " is already installed at " + softwareDir + ".");
        System.exit(0);
      } else {
        System.out
            .println("Updating " + software + " from version " + currentVersion + " to version " + version + "...");
        try (PrintWriter out = new PrintWriter(versionFile)) {
          out.println(version);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      try {
        File newVersionFile = new File(versionFile);
        newVersionFile.createNewFile();
        Files.write(Paths.get(newVersionFile.getPath()), version.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean isMacOs() {

    String osType = System.getProperty("os.name").toLowerCase();
    return osType.contains("mac") || osType.contains("darwin");
  }

  public static boolean isWindows() {

    String osType = System.getProperty("os.name").toLowerCase();
    return osType.contains("windows");
  }

  private static List<String> executeCommand(String[] command, File workingDirectory) {

    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.directory(workingDirectory);
    List<String> output = new ArrayList<>();
    try {
      Process process = processBuilder.start();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          output.add(line);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return output;
  }

  public static void gitPullOrClone(String dir, String url) {

    File localRepoPath = new File(dir);
    File gitDir = new File(localRepoPath, ".git");

    if (gitDir.exists() && gitDir.isDirectory()) {
      String[] gitRemoteCommand = { "git", "remote" };
      List<String> remotes = executeCommand(gitRemoteCommand, localRepoPath);

      if (!remotes.isEmpty()) {
        String[] gitPullCommand = new String[] { "git", "pull" };
        List<String> pullOutput = executeCommand(gitPullCommand, localRepoPath);

        boolean pullFailed = pullOutput.stream().anyMatch(line -> line.contains("error:"));
        if (pullFailed) {
          String errorMessage = "Can not update git repository: " + dir
              + "\nSee above error for details - check your network connectivity and retry.";
          System.err.println(errorMessage);
        }
      } else {
        String message = "This is a local git repo with no remote - if you did this for testing, you may continue...\n";
        message += "Do you want to ignore the problem and continue anyhow?";
        askToContinue(message, false);
      }
    } else {
      if (url == null || url.isEmpty()) {
        System.err.println("Not a git repository: " + dir);
      } else {
        int hashIndex = url.lastIndexOf("#");
        String branch = (hashIndex != -1) ? url.substring(hashIndex + 1) : null;
        if (branch != null) {
          url = url.substring(0, hashIndex);
        }

        String[] gitCloneCommand = new String[] { "git", "clone", "--recursive", url, dir };
        executeCommand(gitCloneCommand, null);

        if (branch != null) {
          String[] gitCheckoutCommand = new String[] { "git", "checkout", branch };
          executeCommand(gitCheckoutCommand, localRepoPath);
        }
      }
    }
  }

  public static void updateUrls() {

    String urlsDir = DEVON_IDE_HOME + "\\urls";
    String devonUrls = EnvironmentCommand.get().get("DEVON_URLS");
    String gitUrl = !devonUrls.equals("") ? devonUrls : "https://github.com/devonfw/ide-urls.git";
    gitPullOrClone(urlsDir, gitUrl);
  }

  public static String getLatestVersion(String software, String edition) {

    Path directoryPath = Paths.get(DEVON_IDE_HOME, "urls", software, edition);
    Path urlsPath = Paths.get(DEVON_IDE_HOME, "urls");
    if (!Files.exists(directoryPath)) {
      gitPullOrClone(String.valueOf(urlsPath), "https://github.com/devonfw/ide-urls.git");
    }
    String latestVersion = null;
    try {
      try (Stream<Path> paths = Files.list(directoryPath)) {
        latestVersion = paths.map(Path::toFile).filter(File::isDirectory).map(File::getName).min((v1, v2) -> {
          String[] parts1;
          String[] parts2;
          if (!v1.matches("\\D")) {
            parts1 = v1.split("[^0-9]+");
          } else {
            parts1 = v1.split("\\.");
          }
          if (!v2.matches("\\D")) {
            parts2 = v2.split("[^0-9]+");
          } else {
            parts2 = v2.split("\\.");
          }
          for (int i = 0; i < Math.min(parts1.length, parts2.length); i++) {
            if (parts1[i].contains("_")) {
              int index = parts1[i].indexOf("_");
              parts1[i] = parts1[i].substring(0, index);
            }
            if (parts2[i].contains("_")) {
              int index = parts2[i].indexOf("_");
              parts2[i] = parts2[i].substring(0, index);
            }
            int comparison = Integer.compare(Integer.parseInt(parts2[i]), Integer.parseInt(parts1[i]));
            if (comparison != 0) {
              return comparison;
            }
          }
          return Integer.compare(parts2.length, parts1.length);
        }).orElse(null);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return latestVersion;
  }

  public static void saveEditionVariable(String software, String edition) {

    String editionVariable = (software + "_EDITION").toUpperCase();
    File devonPropertiesFile = Paths.get(DEVON_IDE_HOME, "settings", "devon.properties").toFile();
    try {
      List<String> lines = Files.readAllLines(devonPropertiesFile.toPath());
      lines = lines.stream().filter(line -> !line.startsWith(editionVariable)).collect(Collectors.toList());
      lines.add(editionVariable + "=" + edition);

      Files.write(devonPropertiesFile.toPath(), lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void extract(String filePath, String targetSubDir) {

    File file = new File(filePath);
    Path targetPath = Paths.get(targetSubDir);
    try {
      Files.createDirectories(targetPath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String fileName = file.getName();
    String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
    System.out.println("Extracting files to " + targetSubDir.toString() + "...");
    switch (fileExtension) {
      case ".tar":
      case ".tgz":
      case ".tbz2":
        extractTar(file, targetPath);
        System.out.println(fileName + " was successfully extracted.");
        break;
      case ".zip":
      case ".jar":
        extractZip(file, targetPath);
        System.out.println(fileName + " was successfully extracted.");
        break;
      default:
        throw new IllegalArgumentException("Unknown archive format: " + fileExtension);
    }
  }

  private static void extractTar(File file, Path targetPath) {

    ProcessBuilder pb = new ProcessBuilder("tar", "xf", file.getAbsolutePath(), "-C", targetPath.toString());
    try {
      Process process = pb.start();
      int exitCode = process.waitFor();
      if (exitCode != 0) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
        throw new RuntimeException("Failed to extract tar file " + file.getAbsolutePath());
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void extractZip(File file, Path targetPath) {

    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      try (ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
          File f = targetPath.resolve(entry.getName()).toFile();
          if (entry.isDirectory()) {
            if (!f.isDirectory() && !f.mkdirs()) {
              throw new IOException("Failed to create directory " + f);
            }
          } else {
            File parent = f.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
              throw new IOException("Failed to create directory " + parent);
            }
            try (OutputStream outputStream = Files.newOutputStream(f.toPath())) {
              byte[] buffer = new byte[1024];
              int len;
              while ((len = zipInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void verifyUrlsExistence() {

    Path urlsPath = Paths.get(DEVON_IDE_HOME, "urls");
    if (!Files.exists(urlsPath)) {
      gitPullOrClone(String.valueOf(urlsPath), "https://github.com/devonfw/ide-urls.git");
    }
  }

  public static void listVersions(String software, String edition) {

    verifyUrlsExistence();
    if (edition == null || edition.isEmpty()) {
      edition = software;
    }
    if (Files.isDirectory(Paths.get(DEVON_IDE_HOME, "urls"))) {
      updateUrls();
      Path versionsPath = Paths.get(DEVON_IDE_HOME, "urls", software, edition);
      List<String> lines = new ArrayList<>();
      try {
        try (Stream<Path> paths = Files.list(versionsPath)) {
          lines = paths.map(Path::toFile).filter(File::isDirectory).map(File::getName).sorted((v1, v2) -> {
            String parts1[];
            String parts2[];
            if (!v1.matches("\\D")) {
              parts1 = v1.split("[^0-9]+");
            } else {
              parts1 = v1.split("\\.");
            }
            if (!v2.matches("\\D")) {
              parts2 = v2.split("[^0-9]+");
            } else {
              parts2 = v2.split("\\.");
            }
            for (int i = 0; i < Math.min(parts1.length, parts2.length); i++) {
              if (parts1[i].contains("_")) {
                int index = parts1[i].indexOf("_");
                parts1[i] = parts1[i].substring(0, index);
              }
              if (parts2[i].contains("_")) {
                int index = parts2[i].indexOf("_");
                parts2[i] = parts2[i].substring(0, index);
              }
              int comparison = Integer.compare(Integer.parseInt(parts2[i]), Integer.parseInt(parts1[i]));
              if (comparison != 0) {
                return comparison;
              }
            }
            return Integer.compare(parts2.length, parts1.length);
          }).collect(Collectors.toList());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("There are " + lines.size() + " available versions. Do You want to list them all?");
      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.print("(yes/no): ");
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("yes") || answer.isEmpty()) {
          System.out.println("listing all available versions:");
          for (String line : lines) {
            System.out.println(line);
          }
          break;
        } else if (answer.equalsIgnoreCase("no")) {
          System.out.println("How many of the last versions do you want to list? please answer with a correct number.");
          String input = scanner.nextLine().trim();
          while (!canParseToLong(input)) {
            System.out.println("please enter a correct number or enter q/quit to cancel");
            input = scanner.nextLine().trim();
            if (input.equals("quit") || input.equals("q")) {
              System.exit(0);
              break;
            }
          }
          long number = Long.parseLong(input);
          System.out.println("listing the last " + number + " versions:");
          for (int i = 0; i < number && i < lines.size(); i++) {
            System.out.println(lines.get(i));
          }
          break;
        } else {
          System.out.println("Please answer yes or no (or hit return for yes).");
        }
      }
    }
  }

  private static boolean canParseToLong(String str) {

    try {
      Long.parseLong(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static void setSoftwareVersion(String software, String version) {

    verifyUrlsExistence();
    if (version != null) {
      if (version.equals("latest")) {
        updateUrls();
        String softwareEdition = software.toUpperCase() + "_EDITION";
        String edition = EnvironmentCommand.get().get(softwareEdition);
        if (edition.isEmpty() || edition == null) {
          edition = software;
        }
        version = getLatestVersion(software, edition);
      }
      String softwareVersionVariable = software.equals("nodejs") ? "node" : software;
      softwareVersionVariable = (softwareVersionVariable + "_VERSION").toUpperCase();
      File devonPropertiesFile = Paths.get(DEVON_IDE_HOME, "settings", "devon.properties").toFile();
      try {
        List<String> lines = Files.readAllLines(devonPropertiesFile.toPath());
        String finalSoftwareVersionVariable = softwareVersionVariable;
        lines = lines.stream().filter(line -> !line.startsWith(finalSoftwareVersionVariable))
            .collect(Collectors.toList());
        lines.add(softwareVersionVariable + "=" + version);

        Files.write(devonPropertiesFile.toPath(), lines);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(softwareVersionVariable + "=" + version + " has been set in " + devonPropertiesFile.getPath()
          + "\nTo install that version call the following command:\ndevon " + software + " setup");
    } else {
      System.err.println("You have to specify the version you want to set.");
    }
  }

  public static Path searchFolder(String path, String folderName) {

    Path binPath = null;
    File folder = new File(path);
    if (folder.exists() && folder.isDirectory()) {
      if (containsFolder(folder, folderName)) {
        String pathString = folder.getAbsolutePath() + "/" + folderName;
        binPath = Path.of(pathString);
      } else {
        File[] files = folder.listFiles();
        if (files != null) {
          for (File file : files) {
            if (file.isDirectory()) {
              return searchFolder(file.getAbsolutePath(), folderName);
            }
          }
        }
      }
    }
    return binPath;
  }

  private static boolean containsFolder(File folder, String folderName) {

    File[] files = folder.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory() && file.getName().equals(folderName)) {
          return true;
        }
      }
    }
    return false;
  }

}
