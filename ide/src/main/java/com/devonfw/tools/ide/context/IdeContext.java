package com.devonfw.tools.ide.context;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.log.IdeLogger;

/**
 * Interface for interaction with the user allowing to input and output information.
 */
public interface IdeContext extends IdeLogger {

  /**
   * @param question the question to ask.
   * @return {@code true} if the user answered with "yes", {@code false} otherwise ("no").
   */
  default boolean question(String question) {

    String yes = "yes";
    String option = question(question, yes, "no");
    if (yes.equals(option)) {
      return true;
    }
    return false;
  }

  /**
   * @param <O> type of the option. E.g. {@link String}.
   * @param question the question to ask.
   * @param options the available options for the user to answer. There should be at least two options given as
   *        otherwise the question cannot make sense.
   * @return the option selected by the user as answer.
   */
  @SuppressWarnings("unchecked")
  <O> O question(String question, O... options);

  /**
   * @return the {@link Environment}.
   */
  Environment env();

}
