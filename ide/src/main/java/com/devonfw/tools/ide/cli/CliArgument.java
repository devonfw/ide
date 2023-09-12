package com.devonfw.tools.ide.cli;

/**
 * A single argument of a {@code main} method from a command-line-interface (CLI).
 *
 * @since 1.0.0
 * @see #getNext(boolean)
 */
public class CliArgument {

  /**
   * The {@link #get() argument} to indicate the end of the options. If this string is given as argument, any further
   * arguments are treated as values. This allows to provide values (e.g. a filename) starting with a hyphen ('-').
   */
  public static final String END_OPTIONS = "--";

  /** A dummy {@link CliArgument} to represent the end of the CLI arguments. */
  public static final CliArgument END = new CliArgument("«end»");

  private final String arg;

  private CliArgument next;

  /**
   * The constructor.
   *
   * @param arg the {@link #get() argument}.
   */
  protected CliArgument(String arg) {

    super();
    this.arg = arg;
    this.next = END;
  }

  /**
   * @return the argument text (e.g. "-h" for a short option, "--help" for a long option, or "foo" for a value).
   */
  public String get() {

    return this.arg;
  }

  /**
   * @return {@code true} if this is an option (e.g. "-h" or "--help"), {@code false} otherwise.
   */
  public boolean isOption() {

    return this.arg.startsWith("-");
  }

  /**
   * @return {@code true} if this is a long option (e.g. "--help"), {@code false} otherwise.
   */
  public boolean isLongOption() {

    return this.arg.startsWith("--");
  }

  /**
   * @return {@code true} if {@link #END_OPTIONS}, {@code false} otherwise.
   */
  public boolean isEndOptions() {

    return this.arg.equals(END_OPTIONS);
  }

  /**
   * @return {@code true} if this is the {@link #END}, {@code false} otherwise.
   */
  public boolean isEnd() {

    return (this == END);
  }

  /**
   * @param splitShortOpts - if {@code true} then combined short options will be split (so instead of "-fbd" you will
   *        get "-f", "-b", "-d").
   * @return the next {@link CliArgument} or {@code null} if this is the last argument.
   */
  public CliArgument getNext(boolean splitShortOpts) {

    if (splitShortOpts) {
      int len = this.arg.length();
      if ((len > 2) && (this.arg.charAt(0) == '-') && (this.arg.charAt(1) != '-')) {
        CliArgument first = null;
        CliArgument current = null;
        for (int i = 1; i < len; i++) {
          CliArgument shortOpt = new CliArgument("-" + this.arg.charAt(i));
          shortOpt.next = this.next;
          if (current == null) {
            first = shortOpt;
          } else {
            current.next = shortOpt;
          }
          current = shortOpt;
        }
        return first;
      }
    }
    return this.next;
  }

  @Override
  public String toString() {

    return this.arg;
  }

  /**
   * @param args the command-line arguments from {@code main} method.
   * @return the first {@link CliArgument} of the parsed arguments or {@code null} if for empty arguments.
   */
  public static CliArgument of(String... args) {

    CliArgument first = CliArgument.END;
    CliArgument current = null;
    for (int argsIndex = 0; argsIndex < args.length; argsIndex++) {
      String arg = args[argsIndex];
      CliArgument argument = new CliArgument(arg);
      if (current == null) {
        first = argument;
      } else {
        current.next = argument;
      }
      current = argument;
    }
    return first;
  }

}
