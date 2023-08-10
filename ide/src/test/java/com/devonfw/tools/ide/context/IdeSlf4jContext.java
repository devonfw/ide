package com.devonfw.tools.ide.context;

import java.nio.file.Path;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.log.IdeLogger;
import com.devonfw.tools.ide.log.IdeSlf4jLogger;

/**
 * Implementation of {@link IdeContext} for testing.
 */
public class IdeSlf4jContext extends AbstractIdeTestContext {

  /**
   * The constructor.
   *
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
  public IdeSlf4jContext(Path userDir, String workspace, Path ideRoot, String userHome, String... answers) {

    super(level -> new IdeSlf4jLogger(level), userDir, workspace, ideRoot, userHome, answers);
  }

}
