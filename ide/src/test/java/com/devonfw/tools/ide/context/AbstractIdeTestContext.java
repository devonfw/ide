package com.devonfw.tools.ide.context;

import java.nio.file.Path;
import java.util.function.Function;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.log.IdeSubLogger;

/**
 * Implementation of {@link IdeContext} for testing.
 */
public class AbstractIdeTestContext extends AbstractIdeContext {

  private final String[] answers;

  private int answerIndex;

  /**
   * The constructor.
   *
   * @param factory the {@link Function} to create {@link IdeSubLogger} per {@link IdeLogLevel}.
   * @param userDir the optional {@link Path} to current working directory See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param workspace the optional {@link Environment#getWorkspaceName() WORKSPACE}. See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param ideRoot the optional {@link Environment#getIdeRoot() IDE_ROOT}. See
   *        {@link Environment#of(IdeLogger, Path, String, Path, String)} for further details.
   * @param userHome the path relative to {@link Environment#getIdeHome() IDE_HOME} for {@link Environment#getUserHome()
   *        HOME} for testing. Typically {@code null} to use the default.
   * @param answers the automatic answers simulating a user in test.
   */
  public AbstractIdeTestContext(Function<IdeLogLevel, IdeSubLogger> factory, Path userDir, String workspace,
      Path ideRoot, String userHome, String... answers) {

    super(IdeLogLevel.TRACE, factory, userDir, workspace, ideRoot, userHome);
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
