package com.devonfw.tools.ide.migrator;

import java.io.File;

import com.devonfw.tools.ide.configurator.Args;
import com.devonfw.tools.ide.logging.Log;
import com.devonfw.tools.ide.logging.Output;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Main class for migration support of a devonfw (e.g. devon4j) project.
 */
public class Migrator {

  /**
   * @param args the command-line arguments.
   */
  public static void main(String[] args) {

    Migrator migrator = new Migrator();
    int exitCode = -1;
    try {
      exitCode = migrator.run(args);
    } catch (Exception e) {
      Output.get().err(e);
    }
    System.exit(exitCode);
  }

  /**
   * @param args the command-line arguments.
   * @return the exit code ({@code 0} for success, any other error code on error).
   */
  private int run(String[] args) throws Exception {

    boolean singleStep = false;
    // currently devon4j is hardcoded, when other stacks want to use this here
    // they are free to implement their migrations and allow a CLI option to
    // use their stack
    MigrationImpl migration = Migrations.devon4j();
    String migrationName = "devon4j";
    File projectFolder = new File(".");
    VersionIdentifier startVersion = null;
    Args arguments = new Args(args);
    while (arguments.hasNext()) {
      String arg = arguments.next();
      if ("from".equals(arg)) {
        if (!arguments.hasNext()) {
          throw new IllegalArgumentException("Commandline option 'from' has to be followed by a version");
        }
        arg = arguments.next();
        if (arg.startsWith("oasp4j:")) {
          startVersion = VersionIdentifier.ofOasp4j(arg.substring(7));
        } else if (arg.startsWith("devon4j:")) {
          startVersion = VersionIdentifier.ofDevon4j(arg.substring(8));
        } else if (arg.startsWith("2.")) {
          startVersion = VersionIdentifier.ofOasp4j(arg);
        } else {
          startVersion = VersionIdentifier.ofDevon4j(arg);
        }
      } else if ("single".equals(arg)) {
        singleStep = true;
      } else if ("step".equals(arg)) {
        if (!arguments.hasNext()) {
          throw new IllegalArgumentException("Commandline option 'step' has to be followed by a identifier of a specific step");
        }
        arg = arguments.next();
        switch(arg) {
          case "junit":
            migration = Migrations.junit();
            break;
        }
      } else {
        throw new IllegalArgumentException("Unknown argument '" + arg + "'");
      }
    }
    Log.init("migrator-" + migrationName, true);
    return migration.migrate(projectFolder, startVersion, singleStep);
  }

}
