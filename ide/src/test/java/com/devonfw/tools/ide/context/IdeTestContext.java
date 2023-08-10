package com.devonfw.tools.ide.context;

import java.nio.file.Path;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.log.IdeTestLogger;

/**
 * Implementation of {@link IdeContext} for testing.
 */
public class IdeTestContext extends AbstractIdeContext {

  private final String[] answers;

  private int answerIndex;

  /**
   * The constructor.
   *
   * @param ideHome the optional {@link Environment#getIdeHome() IDE_HOME}. See
   *        {@link Environment#of(IdeLogger, Path, String)} for further details.
   * @param workspace the optional {@link Environment#getWorkspaceName() WORKSPACE}. See
   *        {@link Environment#of(IdeLogger, Path, String)} for further details.
   * @param answers the automatic answers simulating a user in test.
   */
  public IdeTestContext(Path ideHome, String workspace, String... answers) {

    super(IdeLogLevel.TRACE, level -> new IdeTestLogger(level), ideHome, workspace);
    this.answers = answers;
  }

  @Override
  protected String readLine() {

    if (this.answerIndex >= this.answers.length) {
      throw new IllegalStateException("End of answers reached!");
    }
    return this.answers[this.answerIndex++];
  }

}
