package com.devonfw.tools.ide.context;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.log.IdeSubLogger;
import com.devonfw.tools.ide.log.IdeSubLoggerNone;

/**
 * Abstract base implementation of {@link IdeContext}.
 */
public abstract class AbstractIdeContext implements IdeContext {

  private final Map<IdeLogLevel, IdeSubLogger> loggers;

  private final Environment environment;

  /**
   * The constructor.
   *
   * @param minLogLevel the minimum {@link IdeLogLevel} to enable. Should be {@link IdeLogLevel#INFO} by default.
   * @param factory the {@link Function} to create {@link IdeSubLogger} per {@link IdeLogLevel}.
   * @param userDir the optional {@link Path} to current working directory See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param workspace the optional {@link Environment#getWorkspaceName() WORKSPACE}. See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param ideRoot the optional {@link Environment#getIdeRoot() IDE_ROOT}. See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param userHome the path relative to {@link Environment#getIdeHome() IDE_HOME} for {@link Environment#getUserHome()
   *        HOME} for testing. Typically {@code null} to use the default.
   */
  public AbstractIdeContext(IdeLogLevel minLogLevel, Function<IdeLogLevel, IdeSubLogger> factory, Path userDir,
      String workspace, Path ideRoot, String userHome) {

    super();
    this.loggers = new HashMap<>();
    for (IdeLogLevel level : IdeLogLevel.values()) {
      IdeSubLogger logger;
      if (level.ordinal() < minLogLevel.ordinal()) {
        logger = new IdeSubLoggerNone(level);
      } else {
        logger = factory.apply(level);
      }
      this.loggers.put(level, logger);
    }
    this.environment = Environment.of(this, userDir, workspace, ideRoot, userHome);
  }

  @Override
  public IdeSubLogger level(IdeLogLevel level) {

    IdeSubLogger logger = this.loggers.get(level);
    Objects.requireNonNull(logger);
    return logger;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <O> O question(String question, O... options) {

    assert (options.length >= 2);
    interaction(question);
    Map<String, O> mapping = new HashMap<>(options.length);
    int i = 1;
    for (O option : options) {
      String key = "" + option;
      addMapping(mapping, key, option);
      String numericKey = Integer.toString(i);
      if (numericKey.equals(key)) {
        trace("Options should not be numeric: " + key);
      } else {
        addMapping(mapping, numericKey, option);
      }
      interaction("Option " + numericKey + ": " + key);
    }
    O option = null;
    while (option == null) {
      String answer = readLine();
      option = mapping.get(answer);
      if (option == null) {
        warning("Invalid answer: '" + answer + "' - please try again.");
      }
    }
    return option;
  }

  /**
   * @return the input from the end-user (e.g. read from the console).
   */
  protected abstract String readLine();

  private static <O> void addMapping(Map<String, O> mapping, String key, O option) {

    O duplicate = mapping.put(key, option);
    if (duplicate != null) {
      throw new IllegalArgumentException("Duplicated option " + key);
    }
  }

  @Override
  public Environment env() {

    return this.environment;
  }

}
