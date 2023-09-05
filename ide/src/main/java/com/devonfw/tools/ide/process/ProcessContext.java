package com.devonfw.tools.ide.process;

import java.nio.file.Path;
import java.util.List;

/**
 * Wrapper for {@link ProcessBuilder} to simplify its usage and avoid common mistakes and pitfalls.
 */
public interface ProcessContext {

  /** Exit code for success. */
  int SUCCESS = 0;

  /**
   * @return the underlying {@link ProcessBuilder} instance.
   */
  ProcessBuilder getProcessBuilder();

  /**
   * @param handling the desired {@link ProcessErrorHandling}.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  ProcessContext errorHandling(ProcessErrorHandling handling);

  /**
   * @param directory the {@link Path} to the working directory from where to execute the command.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  default ProcessContext directory(Path directory) {

    getProcessBuilder().directory(directory.toFile());
    return this;
  }

  /**
   * @param command the command to run.
   * @return the exit code. Will be {@link #SUCCESS} on successful completion of the {@link Process}.
   */
  int run(String... command);

  /**
   * @param commands the list of commands to run.
   * @return the exit code. Will be {@link #SUCCESS} on successful completion of the {@link Process}.
   */
  int run(List<String> commands);

  /**
   * @param command the command to run.
   * @return a {@link List} with the standard output of the command, line by line.
   */
  List<String> runAndGetStdOut(String... command);

  /**
   * @param commands the list of commands to run.
   * @return a {@link List} with the standard output of the command, line by line.
   */
  List<String> runAndGetStdOut(List<String> commands);

}
