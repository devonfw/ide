package com.devonfw.tools.ide.logging;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.devonfw.tools.ide.configurator.Configurator;

/**
 * This class provides a {@link Logger} for logging purposes. To minimize dependencies we use the JDK default logger.
 *
 * @author trippl
 * @since 3.0.0
 */
public class Log {

  /** Logger instance. */
  private static final Logger LOGGER = Logger.getLogger(Configurator.class.getName());

  /**
   * @param filename the base filename of the logger.
   */
  public static void init(String filename) {

    try {
      String logFolderName = "log";
      if (!new File("scripts").exists()) {
        logFolderName = "target";
      }
      File logFolder = new File(logFolderName);
      File logFile = new File(logFolder, filename + ".log");
      if (logFile.exists()) {
        logFile.delete();
      }
      logFolder.mkdirs();
      FileHandler logFileHandler = new FileHandler(logFile.getPath());
      logFileHandler.setLevel(Level.FINEST);
      LogFormatter formatter = new LogFormatter();
      logFileHandler.setFormatter(formatter);
      LOGGER.addHandler(logFileHandler);
      LOGGER.setLevel(Level.FINEST);
      Logger globalLogger = Logger.getLogger("");
      globalLogger.setLevel(Level.FINEST);
      for (Handler handler : globalLogger.getHandlers()) {
        handler.setFormatter(formatter);
      }
    } catch (Exception e) {
      System.err.println("Could not open log file");
      e.printStackTrace();
    }
  }

  /**
   * @param message the trace message to log.
   */
  public static void trace(String message) {

    Log.LOGGER.finest(message);
  }

  /**
   * @param message the debug message to log.
   */
  public static void debug(String message) {

    Log.LOGGER.fine(message);
  }

  /**
   * @param message the info message to log.
   */
  public static void info(String message) {

    System.out.println(message);
    Log.LOGGER.info(message);
  }

  /**
   * @param message the warning message to log.
   */
  public static void warn(String message) {

    System.err.println(message);
    Log.LOGGER.warning(message);
  }

  /**
   * @param message the error message to log.
   */
  public static void err(String message) {

    System.err.println(message);
    Log.LOGGER.severe(message);
  }

  /**
   * @param message the error message to log.
   * @param e the {@link Throwable} to log.
   */
  public static void err(String message, Throwable e) {

    System.err.println(message);
    Log.LOGGER.log(Level.SEVERE, message, e);
  }

  /**
   * Custom log {@link Formatter} to overrule ugly JUL defaults.
   */
  public static class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {

      return String.format("%1$s [%2$-7s] %3$s\n",
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date(record.getMillis())),
          record.getLevel().getName(), formatMessage(record));
    }
  }
}
