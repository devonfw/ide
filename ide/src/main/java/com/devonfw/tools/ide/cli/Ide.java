package com.devonfw.tools.ide.cli;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import com.devonfw.tools.ide.commandlet.Commandlet;
import com.devonfw.tools.ide.commandlet.ContextCommandlet;
import com.devonfw.tools.ide.context.AbstractIdeContext;
import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.context.IdeContextConsole;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.process.ProcessResult;
import com.devonfw.tools.ide.property.BooleanProperty;
import com.devonfw.tools.ide.property.FlagProperty;
import com.devonfw.tools.ide.property.KeywordProperty;
import com.devonfw.tools.ide.property.Property;

/**
 * The main program of the CLI (command-line-interface).
 */
public final class Ide {

  private AbstractIdeContext context;

  /**
   * The actualy main method of the CLI program.
   *
   * @param args the command-line arguments.
   */
  public static void main(String... args) {

    int exitStatus = new Ide().run(args);
    System.exit(exitStatus);
  }

  private IdeContext context() {

    if (this.context == null) {
      // fallback in case of exception before initialization
      this.context = new IdeContextConsole(IdeLogLevel.INFO, null, false);
    }
    return this.context;
  }

  /**
   * Non-static variant of {@link #main(String...) main method} without invoking {@link System#exit(int)} so it can be
   * tested.
   *
   * @param args the command-line arguments.
   * @return the exit code.
   */
  public int run(String... args) {

    int exitStatus;
    try {
      exitStatus = runOrThrow(args);
    } catch (CliException error) {
      exitStatus = error.getExitCode();
      if (context().level(IdeLogLevel.DEBUG).isEnabled()) {
        context().error(error.getMessage(), error);
      } else {
        context().error(error.getMessage());
      }
    } catch (Throwable error) {
      exitStatus = 255;
      String title = error.getMessage();
      if (title == null) {
        title = error.getClass().getName();
      } else {
        title = error.getClass().getSimpleName() + ": " + title;
      }
      String message = "An unexpected error occurred!\n" //
          + "We are sorry for the inconvenience.\n" //
          + "Please check the error below, resolve it and try again.\n" //
          + "If the error is not on your end (network connectivity, lack of permissions, etc.) please file a bug:\n" //
          + "https://github.com/devonfw/ide/issues/new?assignees=&labels=bug&projects=&template=bug.md&title="
          + URLEncoder.encode(title, StandardCharsets.UTF_8);
      context().error(error, message);
    }
    return exitStatus;
  }

  /**
   * Like {@link #run(String...)} but does not catch {@link Throwable}s so you can handle them yourself.
   *
   * @param args the command-line arguments.
   * @return the exit code.
   */
  public int runOrThrow(String... args) {

    CliArgument first = CliArgument.of(args);
    CliArgument current = initContext(first);
    for (Commandlet commandlet : this.context.getCommandletManager().getCommandlets()) {
      boolean matches = apply(current, commandlet);
      if (matches) {
        this.context.debug("Running commandlet {}", commandlet);
        if (commandlet.isIdeHomeRequired() && (this.context.getIdeHome() == null)) {
          throw new CliException(this.context.getMessageIdeHome());
        }
        commandlet.run();
        return ProcessResult.SUCCESS;
      } else {
        this.context.trace("Commandlet did not match");
      }
    }
    // TODO print help properly
    context().info("Usage: ide «args»");
    return 1;
  }

  private CliArgument initContext(CliArgument first) {

    ContextCommandlet init = new ContextCommandlet();
    CliArgument current = first;
    while (!current.isEnd()) {
      String arg = current.get();
      FlagProperty property = (FlagProperty) init.getOption(arg);
      if (property == null) {
        break;
      }
      property.setValue(Boolean.TRUE);
      current = current.getNext(true);
    }
    init.run();
    this.context = init.getIdeContext();
    return current;
  }

  private boolean apply(CliArgument argument, Commandlet commandlet) {

    this.context.trace("Trying to match arguments to commandlet {}", commandlet.getName());
    CliArgument currentArgument = argument;
    Iterator<Property<?>> valueIterator = commandlet.getValues().iterator();
    Property<?> currentProperty = null;
    boolean endOpts = false;
    while (!currentArgument.isEnd()) {
      if (currentArgument.isEndOptions()) {
        endOpts = true;
      } else {
        String arg = currentArgument.get();
        this.context.trace("Trying to match argument '{}'", argument);
        if ((currentProperty != null) && (currentProperty.isExpectValue())) {
          currentProperty.setValueAsString(arg);
          if (!currentProperty.isMultiValued()) {
            currentProperty = null;
          }
        } else {
          Property<?> property = null;
          if (!endOpts) {
            property = commandlet.getOption(arg);
          }
          if (property == null) {
            if (!valueIterator.hasNext()) {
              this.context.trace("No option or next value found");
              return false;
            }
            currentProperty = valueIterator.next();
            this.context.trace("Next value candidate is {}", currentProperty);
            if (currentProperty instanceof KeywordProperty) {
              KeywordProperty keyword = (KeywordProperty) currentProperty;
              if (keyword.matches(arg)) {
                keyword.setValue(Boolean.TRUE);
                this.context.trace("Keyword matched");
              } else {
                this.context.trace("Missing keyword");
                return false;
              }
            } else {
              currentProperty.setValueAsString(arg);
            }
            currentProperty = null;
          } else {
            this.context.trace("Found option by name");
            if (property instanceof BooleanProperty) {
              ((BooleanProperty) property).setValue(Boolean.TRUE);
            } else {
              currentProperty = property;
              if (property.isEndOptions()) {
                endOpts = true;
              }
              throw new UnsupportedOperationException("not implemented");
            }
          }
        }
      }
      currentArgument = currentArgument.getNext(!endOpts);
    }
    return commandlet.validate();
  }

}