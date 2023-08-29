package com.devonfw.tools.ide.env.var;

import java.nio.file.Path;

import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Abstract base implementation of {@link EnvironmentVariables}.
 */
public abstract class AbstractEnvironmentVariables implements EnvironmentVariables {

  /** @see #getParent() */
  protected final EnvironmentVariables parent;

  /** The {@link IdeLogger} instance. */
  protected final IdeLogger logger;

  private String source;

  /**
   * The constructor.
   *
   * @param parent the parent {@link EnvironmentVariables} to inherit from.
   * @param logger the {@link IdeLogger}.
   */
  public AbstractEnvironmentVariables(EnvironmentVariables parent, IdeLogger logger) {

    super();
    this.parent = parent;
    if (logger == null) {
      if (parent == null) {
        throw new IllegalArgumentException("parent and logger must not both be null!");
      }
      this.logger = ((AbstractEnvironmentVariables) parent).logger;
    } else {
      this.logger = logger;
    }
  }

  @Override
  public EnvironmentVariables getParent() {

    return this.parent;
  }

  @Override
  public Path getPropertiesFilePath() {

    return null;
  }

  @Override
  public String getSource() {

    if (this.source == null) {
      this.source = getType().toString();
      Path propertiesPath = getPropertiesFilePath();
      if (propertiesPath != null) {
        this.source = this.source + "@" + propertiesPath;
      }
    }
    return this.source;
  }

  /**
   * @param propertiesFilePath the {@link #getPropertiesFilePath() propertiesFilePath} of the child
   *        {@link EnvironmentVariables}.
   * @param type the {@link #getType() type}.
   * @return the new {@link EnvironmentVariables}.
   */
  public AbstractEnvironmentVariables extend(Path propertiesFilePath, EnvironmentVariablesType type) {

    return new EnvironmentVariablesPropertiesFile(this, type, propertiesFilePath, this.logger);
  }

  /**
   * @return a new child {@link EnvironmentVariables} that will resolve variables recursively or this instance itself if
   *         already satisfied.
   */
  public EnvironmentVariables resolved() {

    return new EnvironmentVariablesResolved(this);
  }

  @Override
  public String toString() {

    return getSource();
  }

}
