package com.devonfw.ide.configurator.logging;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.devonfw.ide.configurator.Configurator;

/**
 * This class provides a {@link Logger} for logging purposes. To minimize dependencies we use the JDK default logger.
 *
 * @author trippl
 * @since 3.0.0
 */
public class Log {

  /** Logger instance. */
  public static final Logger LOGGER = Logger.getLogger(Configurator.class.getName());

  static {
    try {
      FileHandler logFileHandler = new FileHandler("Configurator.log");
      logFileHandler.setLevel(Level.ALL);
      logFileHandler.setFormatter(new SimpleFormatter());
      LOGGER.addHandler(logFileHandler);
    } catch (Exception e) {
      System.err.println("Could not open log file");
    }
  }

  public static void info(String message) {

    System.out.println(message);
    Log.LOGGER.info(message);
  }

  public static void warn(String message) {

    System.err.println(message);
    Log.LOGGER.warning(message);
  }

  public static void err(String message) {

    System.err.println(message);
    Log.LOGGER.severe(message);
  }

}
