package com.devonfw.tools.ide.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.devonfw.tools.ide.cli.CliException;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.log.IdeSubLogger;

/**
 * Implementation of {@link ProcessContext}.
 */
public final class ProcessContextImpl implements ProcessContext {

  private final ProcessBuilder processBuilder;

  private final IdeLogger logger;

  private ProcessErrorHandling errorHandling;

  /**
   * The constructor.
   *
   * @param processBuilder the {@link ProcessBuilder}.
   * @param logger the {@link IdeLogger}.
   */
  public ProcessContextImpl(ProcessBuilder processBuilder, IdeLogger logger) {

    super();
    this.processBuilder = processBuilder;
    this.logger = logger;
    this.errorHandling = ProcessErrorHandling.THROW;
  }

  @Override
  public ProcessBuilder getProcessBuilder() {

    return this.processBuilder;
  }

  @Override
  public ProcessContext errorHandling(ProcessErrorHandling handling) {

    Objects.requireNonNull(handling);
    this.errorHandling = handling;
    return this;
  }

  @Override
  public int run(String... command) {

    return runCommand(null, command);
  }

  @Override
  public int run(List<String> commands) {

    return runCommand(null, commands);
  }

  @Override
  public List<String> runAndGetStdOut(String... command) {

    List<String> out = new ArrayList<>();
    runCommand(out, command);
    return out;
  }

  @Override
  public List<String> runAndGetStdOut(List<String> command) {

    List<String> out = new ArrayList<>();
    runCommand(out, command);
    return out;
  }
  
  private int runCommand(List<String> out, List<String> commands){
    if ((commands == null) || (commands.isEmpty())) {
      throw new IllegalArgumentException("Commands must not be empty!");
    }

    this.processBuilder.command(commands);
    return processCommand(out, commands.toString());
  }

  private int runCommand(List<String> out, String... command) {

    if ((command == null) || (command.length < 1) || command[0].isBlank()) {
      throw new IllegalArgumentException("Command must not be empty!");
    }

    this.processBuilder.command(command);
    return processCommand(out, command);
  }

  private int processCommand(List<String> out, String... command) {

    if (this.logger.debug().isEnabled()) {
      String message = createCommandMessage(" ...", command);
      this.logger.debug(message);
    }

    try {
      if (out == null) {
        this.processBuilder.redirectOutput(Redirect.INHERIT);
      } else {
        this.processBuilder.redirectOutput(Redirect.PIPE);
      }
      Process process = this.processBuilder.start();
      int exitCode;
      if (out == null) {
        exitCode = process.waitFor();
      } else {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
          while (true) {
            String line = reader.readLine();
            if (line == null) {
              break;
            }
            out.add(line);
          }
        }
        exitCode = process.exitValue();
      }
      if ((exitCode != SUCCESS) && (this.errorHandling != ProcessErrorHandling.NONE)) {
        String message = createCommandMessage(" failed with exit code " + exitCode + "!", command);
        if (this.errorHandling == ProcessErrorHandling.THROW) {
          throw new CliException(message, exitCode);
        }
        IdeSubLogger level;
        if (this.errorHandling == ProcessErrorHandling.ERROR) {
          level = this.logger.error();
        } else if (this.errorHandling == ProcessErrorHandling.WARNING) {
          level = this.logger.warning();
        } else {
          level = this.logger.error();
          level.log("Internal error: Undefined error handling {}", this.errorHandling);
        }
        level.log(message);
      }
      return exitCode;
    } catch (Exception e) {
      String msg = e.getMessage();
      if ((msg == null) || msg.isEmpty()) {
        msg = e.getClass().getSimpleName();
      }
      throw new IllegalStateException(createCommandMessage(" failed: " + msg, command), e);
    }
  }

  private String createCommandMessage(String suffix, String... command) {

    StringBuilder sb = new StringBuilder();
    sb.append("Running command '");
    sb.append(command[0]);
    sb.append("'");
    if (command.length > 1) {
      sb.append(" with arguments");
      for (int i = 1; i < command.length; i++) {
        sb.append(" '");
        sb.append(command[i]);
        sb.append("'");
      }
    }
    sb.append(suffix);
    String message = sb.toString();
    return message;
  }

}
