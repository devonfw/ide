package com.devonfw.ide.configurator.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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

  public static class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {

      return String.format("%1$s [%2$-7s] %3$s\n",
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date(record.getMillis())),
          record.getLevel().getName(), formatMessage(record));
    }
  }
}
