package com.devonfw.tools.ide.migrator.builder;

import java.util.ArrayList;
import java.util.List;

import com.devonfw.tools.ide.logging.Output;
import com.devonfw.tools.ide.migrator.MigrationStep;
import com.devonfw.tools.ide.migrator.MigrationImpl;
import com.devonfw.tools.ide.migrator.version.MavenVersionDetector;
import com.devonfw.tools.ide.migrator.version.VersionDetector;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * Top-level builder to {@link #build() build} a {@link MigrationImpl}.
 */
public class MigrationBuilder {

  private VersionIdentifier version;

  final List<MigrationStep> steps;

  /**
   * The constructor.
   *
   * @param from the initial starting point from where the migration is supported.
   */
  public MigrationBuilder(VersionIdentifier from) {

    super();
    this.version = from;
    this.steps = new ArrayList<>();
  }

  public MigrationBuilder() {
    this(null);
  }

  /**
   * @param to the {@link VersionIdentifier} to migrate to.
   * @return the builder to configure the {@link MigrationStep} to migrate to that given {@link VersionIdentifier}.
   */
  public MigrationStepBuilder to(VersionIdentifier to) {

    VersionIdentifier from = this.version;
    this.version = to;
    return new MigrationStepBuilder(this, from, to);
  }

  /**
   * @return the build {@link MigrationImpl}.
   */
  public MigrationImpl build() {

    VersionDetector versionDetector = new MavenVersionDetector();
    return new MigrationImpl(versionDetector, this.steps.toArray(new MigrationStep[this.steps.size()]));
  }

  /**
   * @param version the initial supported {@link VersionIdentifier}.
   * @param output the {@link Output}.
   * @return {@code this}.
   */
  public static MigrationBuilder from(VersionIdentifier version, Output output) {

    return new MigrationBuilder(version);
  }

}
