package com.devonfw.tools.ide.migrator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.devonfw.tools.ide.logging.Output;
import com.devonfw.tools.ide.migrator.version.VersionDetector;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Implementation of {@link Migration} that combines all {@link MigrationStep}s.
 */
public class MigrationImpl implements Migration {

  private final VersionDetector versionDetector;

  private final Map<VersionIdentifier, MigrationStep> fromVersion2MigrationStepMap;

  /**
   * The constructor.
   *
   * @param versionDetector the {@link VersionDetector}.
   * @param steps the {@link MigrationStep}s.
   */
  public MigrationImpl(VersionDetector versionDetector, MigrationStep... steps) {

    super();
    this.versionDetector = versionDetector;
    this.fromVersion2MigrationStepMap = new HashMap<>();
    for (MigrationStep step : steps) {
      this.fromVersion2MigrationStepMap.put(step.getFrom(), step);
    }
  }

  @Override
  public void migrate(File projectFolder) throws Exception {

    migrate(projectFolder, false);
  }

  /**
   * @param projectFolder the {@link File} (or directory) to migrate.
   * @param singleStep - {@code true} to only migrate to the next version, {@code false} otherwise (migrate to latest
   *        version).
   * @throws Exception on error.
   */
  public void migrate(File projectFolder, boolean singleStep) throws Exception {

    migrate(projectFolder, null, singleStep);
  }

  /**
   * @param projectFolder the {@link File} (or directory) to migrate.
   * @param startVersion the current version of the project to migrate. Typically {@code null} for auto-detection.
   *        However, if that fails it may be provided manually.
   * @param singleStep - {@code true} to only migrate to the next version, {@code false} otherwise (migrate to latest
   *        version).
   * @return the status code with {@code 0} for success and anything else for an error.
   */
  public int migrate(File projectFolder, VersionIdentifier startVersion, boolean singleStep) {

    if (startVersion == null) {
      try {
        startVersion = this.versionDetector.detectVersion(projectFolder);
      } catch (Exception e) {
        Output.get().err("Failed to determine start version for migration!", e.getMessage());
        e.printStackTrace();
        return -1;
      }
    }
    int migrations = 0;
    VersionIdentifier version = startVersion;
    while (true) {
      MigrationStep step = this.fromVersion2MigrationStepMap.get(version);
      if (step == null) {
        return complete(migrations, startVersion, version);
      } else {
        try {
          Output.get().banner("Migrating from version %s to %s ...", step.getFrom().toString(),
              step.getTo().toString());
          step.migrate(projectFolder);
          if (singleStep) {
            return 0;
          }
          migrations++;
        } catch (Exception e) {
          Output.get().err("Migration from %s to %s failed: %s", step.getFrom().toString(), step.getTo().toString(),
              e.getMessage());
          e.printStackTrace();
          return -1;
        }
        version = step.getTo();
      }
    }
  }

  /**
   * @param migrations number of migrations that have been applied.
   * @param startVersion the initial {@link VersionIdentifier} before the migration.
   * @param endVersion the final {@link VersionIdentifier} after the migration.
   */
  private int complete(int migrations, VersionIdentifier startVersion, VersionIdentifier endVersion) {

    if (migrations == 0) {
      Output.get().warn("Project is already on version %s. No migrations available to update.",
          startVersion.toString());
      return 1;
    } else {
      Output.get().banner("Successfully applied %s migrations to migrate project from version %s to %s.",
          Integer.toString(migrations), startVersion.toString(), endVersion.toString());
      return 0;
    }
  }

  /**
   * @param start the {@link VersionIdentifier} to with the current version to start from.
   * @return the {@link MigrationStep} to migrate to the next version.
   */
  public MigrationStep get(VersionIdentifier start) {

    return this.fromVersion2MigrationStepMap.get(start);
  }

}
