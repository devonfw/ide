package com.devonfw.tools.ide.commandlet;

import java.util.concurrent.Callable;

import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.context.IdeContextConsole;
import com.devonfw.tools.ide.log.IdeLogLevel;

/**
 * Abstract base class of a commandlet of the IDE CLI.
 */
public abstract class Commandlet implements Callable<Integer> {

  private IdeContext context;

  private static final Integer SUCCESS = 0;

  @Override
  public Integer call() {

    System.out.println("Running command " + getClass().getSimpleName());
    run();
    return SUCCESS;
  }

  /**
   * @param ideContext the {@link IdeContext} to initialize in this {@link Commandlet}.
   */
  protected void initContext(IdeContext ideContext) {

    if (this.context == null) {
      this.context = ideContext;
    } else {
      throw new IllegalStateException("Context is already initialized!");
    }
  }

  /**
   * @return the {@link IdeContext} used to interact with the user (output messages, ask questions and read answers).
   */
  protected IdeContext context() {

    if (this.context == null) {
      this.context = new IdeContextConsole(IdeLogLevel.INFO, null, false);
      this.context.warning("context was not set");
    }
    return this.context;
  }

  /**
   * Runs this {@link Commandlet}.
   */
  public abstract void run();
}
