package com.devonfw.tools.ide.process;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper for {@link ProcessBuilder} to simplify its usage and avoid common mistakes and pitfalls.
 */
public interface ProcessContext {

  /**
   * Exit code for success.
   *
   * @deprecated use {@link ProcessResult#SUCCESS}
   */
  @Deprecated
  int SUCCESS = 0;

  /**
   * @param handling the desired {@link ProcessErrorHandling}.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  ProcessContext errorHandling(ProcessErrorHandling handling);

  /**
   * @param directory the {@link Path} to the working directory from where to execute the command.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  ProcessContext directory(Path directory);

  /**
   * Sets the executable command to be {@link #run()}.
   *
   * @param executable the {@link Path} to the command to be executed by {@link #run()}. Depending on your operating
   *        system and the extension of the executable or OS specific conventions. So e.g. a *.cmd or *.bat file will be
   *        called via CMD shell on windows while a *.sh file will be called via Bash, etc.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  ProcessContext executable(Path executable);

  /**
   * Sets the executable command to be {@link #run()}.
   *
   * @param executable the command to be executed by {@link #run()}.
   * @return this {@link ProcessContext} for fluent API calls.
   * @see #executable(Path)
   */
  default ProcessContext executable(String executable) {

    return executable(Paths.get(executable));
  }

  /**
   * Adds a single argument for {@link #executable(Path) command}.
   *
   * @param arg the next argument for {@link #executable(Path) command} to be added.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  ProcessContext addArg(String arg);

  /**
   * Adds a single argument for {@link #executable(Path) command}.
   *
   * @param arg the next argument for {@link #executable(Path) command} to be added.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  default ProcessContext addArg(Object arg) {

    Objects.requireNonNull(arg);
    return addArg(arg.toString());
  }

  /**
   * Adds the given arguments for {@link #executable(Path) command}. E.g. for {@link #executable(Path) command} "mvn"
   * the arguments "clean" and "install" may be added here to run "mvn clean install". If this method would be called
   * again with "-Pmyprofile" and "-X" before {@link #run()} gets called then "mvn clean install -Pmyprofile -X" would
   * be run.
   *
   * @param args the arguments for {@link #executable(Path) command} to be added.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  default ProcessContext addArgs(String... args) {

    for (String arg : args) {
      addArg(arg);
    }
    return this;
  }

  /**
   * Adds the given arguments for {@link #executable(Path) command} as arbitrary Java objects. It will add the
   * {@link Object#toString() string representation} of these arguments to the command.
   *
   * @param args the arguments for {@link #executable(Path) command} to be added.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  default ProcessContext addArgs(Object... args) {

    for (Object arg : args) {
      addArg(arg);
    }
    return this;
  }

  /**
   * Adds the given arguments for {@link #executable(Path) command} as arbitrary Java objects. It will add the
   * {@link Object#toString() string representation} of these arguments to the command.
   *
   * @param args the {@link List} of arguments for {@link #executable(Path) command} to be added.
   * @return this {@link ProcessContext} for fluent API calls.
   */
  default ProcessContext addArgs(List<?>... args) {

    for (Object arg : args) {
      addArg(arg);
    }
    return this;
  }

  /**
   * Runs the previously configured {@link #executable(Path) command} with the configured {@link #addArgs(String...)
   * arguments}. Will reset the {@link #addArgs(String...) arguments} but not the {@link #executable(Path) command} for
   * sub-sequent calls.
   *
   * @return the exit code. Will be {@link #SUCCESS} on successful completion of the {@link Process}.
   */
  default int run() {

    return run(false).getExitCode();
  }

  /**
   * Runs the previously configured {@link #executable(Path) command} with the configured {@link #addArgs(String...)
   * arguments}. Will reset the {@link #addArgs(String...) arguments} but not the {@link #executable(Path) command} for
   * sub-sequent calls.
   *
   * @param capture - {@code true} to capture standard {@link ProcessResult#getOut() out} and
   *        {@link ProcessResult#getErr() err} in the {@link ProcessResult}, {@code false} otherwise (to redirect out
   *        and err).
   * @return the {@link ProcessResult}.
   */
  ProcessResult run(boolean capture);

  /**
   * Runs the previously configured {@link #executable(Path) command} with the configured {@link #addArgs(String...)
   * arguments}. Will reset the {@link #addArgs(String...) arguments} but not the {@link #executable(Path) command} for
   * sub-sequent calls.
   *
   * @return a {@link List} with the standard output of the command, line by line.
   * @deprecated use {@link #run(boolean)} instead.
   */
  @Deprecated
  default List<String> runAndGetStdOut() {

    return run(true).getOut();
  }

  /**
   * @param command the command to run.
   * @return the exit code. Will be {@link #SUCCESS} on successful completion of the {@link Process}.
   * @deprecated use {@link #executable(Path)}, {@link #addArgs(String...)} and {@link #run()} instead.
   */
  @Deprecated
  default int run(String... command) {

    executable(Paths.get(command[0]));
    for (int i = 1; i < command.length; i++) {
      addArg(command[i]);
    }
    return run();
  }

  /**
   * @param commands the list of commands to run.
   * @return the exit code. Will be {@link #SUCCESS} on successful completion of the {@link Process}.
   * @deprecated use {@link #executable(Path)}, {@link #addArgs(String...)} and {@link #run()} instead.
   */
  @Deprecated
  default int run(List<String> commands) {

    executable(Paths.get(commands.get(0)));
    int size = commands.size();
    for (int i = 1; i < size; i++) {
      addArg(commands.get(i));
    }
    return run();
  }

  /**
   * @param command the command to run.
   * @return a {@link List} with the standard output of the command, line by line.
   * @deprecated use {@link #executable(Path)}, {@link #addArgs(String...)} and {@link #runAndGetStdOut()} instead.
   */
  @Deprecated
  default List<String> runAndGetStdOut(String... command) {

    executable(Paths.get(command[0]));
    for (int i = 1; i < command.length; i++) {
      addArg(command[i]);
    }
    return runAndGetStdOut();
  }

  /**
   * @param commands the list of commands to run.
   * @return a {@link List} with the standard output of the command, line by line.
   * @deprecated use {@link #executable(Path)}, {@link #addArgs(String...)} and {@link #runAndGetStdOut()} instead.
   */
  @Deprecated
  default List<String> runAndGetStdOut(List<String> commands) {

    executable(Paths.get(commands.get(0)));
    int size = commands.size();
    for (int i = 1; i < size; i++) {
      addArg(commands.get(i));
    }
    return runAndGetStdOut();
  }

}
