package com.devonfw.tools.ide.context;

import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeSubLoggerOut;

/**
 * Default implementation of {@link IdeContext} using the console.
 */
public class IdeContextConsole extends AbstractIdeContext {

  /**
   * The constructor.
   *
   * @param out the {@link Appendable} to {@link Appendable#append(CharSequence) write} log messages to.
   * @param minLogLevel the minimum {@link IdeLogLevel} to enable. Should be {@link IdeLogLevel#INFO} by default.
   * @param colored - {@code true} for colored output according to {@link IdeLogLevel}, {@code false} otherwise.
   */
  public IdeContextConsole(IdeLogLevel minLogLevel, Appendable out, boolean colored) {

    super(minLogLevel, level -> new IdeSubLoggerOut(level, out, colored), null, null, null, null);
  }

  @Override
  protected java.lang.String readLine() {

    return System.console().readLine();
  }

}
