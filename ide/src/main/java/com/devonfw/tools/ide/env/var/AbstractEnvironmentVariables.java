package com.devonfw.tools.ide.env.var;

import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Abstract base implementation of {@link EnvironmentVariables}.
 */
public abstract class AbstractEnvironmentVariables implements EnvironmentVariables {

  /** @see #getParent() */
  protected final EnvironmentVariables parent;

  /** @see #getSource() */
  protected final String source;

  /** The {@link IdeLogger} instance. */
  protected final IdeLogger logger;

  /**
   * The constructor.
   *
   * @param parent the parent {@link EnvironmentVariables} to inherit from.
   * @param source the {@link #getSource() source}.
   * @param logger the {@link IdeLogger}.
   */
  public AbstractEnvironmentVariables(EnvironmentVariables parent, String source, IdeLogger logger) {

    super();
    this.parent = parent;
    this.source = source;
    this.logger = logger;
  }

  @Override
  public EnvironmentVariables getParent() {

    return this.parent;
  }

  @Override
  public String getSource() {

    return this.source;
  }

  @Override
  public EnvironmentVariables resolved() {

    return new EnvironmentVariablesResolved(this);
  }

  @Override
  public String toString() {

    return getSource();
  }

}
