package com.devonfw.tools.ide.commandlet;

import java.util.concurrent.Callable;

import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.context.IdeContextConsole;
import com.devonfw.tools.ide.log.IdeLogLevel;

import picocli.CommandLine;

/**
 * Abstract base class of a commandlet of the IDE CLI.
 */
public abstract class Commandlet implements Callable<Integer> {

  private IdeContext context;

  private static final Integer SUCCESS = 0;

  @CommandLine.Option(names = { "-d", "--debug" }, description = "enable debug mode (verbose logging)")
  private boolean debug;

  @CommandLine.Option(names = { "-b", "--batch" }, description = "enable batch mode (non-interactive)")
  private boolean batch;

  @CommandLine.Option(names = { "-f", "--force" }, description = "enable force mode")
  private boolean force;

  @CommandLine.Option(names = { "-q", "--quiet" }, description = "enable quiet mode (mimimum logging)")
  private boolean quiet;

  @CommandLine.Option(names = { "-o", "--offline" }, description = "enable offline mode")
  private boolean offline;

  @Override
  public Integer call() {

    IdeLogLevel logLevel = IdeLogLevel.INFO;
    if (this.debug) {
      logLevel = IdeLogLevel.DEBUG;
    }
    IdeContextConsole ctx = new IdeContextConsole(logLevel, null, true);
    ctx.setBatchMode(this.batch);
    ctx.setForceMode(this.force);
    ctx.setQuietMode(this.quiet);
    ctx.setOfflineMode(this.offline);
    initContext(ctx);
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
      initContext(new IdeContextConsole(IdeLogLevel.INFO, null, false));
    }
    return this.context;
  }

  /**
   * Runs this {@link Commandlet}.
   */
  public abstract void run();
}
