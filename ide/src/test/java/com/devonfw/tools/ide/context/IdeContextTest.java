package com.devonfw.tools.ide.context;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

import com.devonfw.tools.ide.env.Environment;
import com.devonfw.tools.ide.env.var.EnvironmentVariables;
import com.devonfw.tools.ide.env.var.EnvironmentVariablesType;
import com.devonfw.tools.ide.env.var.def.IdeVariables;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.log.IdeTestLogger;

/**
 * Integration test of {@link IdeContext} including {@link Environment}.
 */
@SuppressWarnings("javadoc")
public class IdeContextTest extends Assertions {

  private static final Path PATH_PROJECTS = Paths.get("src/test/resources/ide-projects");

  private IdeContext newContext(String path) {

    Path userDir = PATH_PROJECTS.resolve(path);
    return new IdeTestContext(userDir, null, null, "home");
  }

  protected void assertLogMessage(IdeContext context, IdeLogLevel level, String message) {

    assertLogMessage(context, level, message, false);
  }

  protected void assertLogMessage(IdeContext context, IdeLogLevel level, String message, boolean contains) {

    IdeTestLogger logger = (IdeTestLogger) context.level(IdeLogLevel.WARNING);
    ListAssert<String> assertion = assertThat(logger.getMessages()).as(level.name() + "-Log messages");
    if (contains) {
      Condition<String> condition = new Condition<>() {
        public boolean matches(String e) {

          return e.contains(message);
        };
      };
      assertion.filteredOn(condition).isNotEmpty();
    } else {
      assertion.contains(message);
    }
  }

  /**
   * Test of {@link IdeContext} initialization from basic project.
   */
  @Test
  public void testBasicProjectEnvironment() {

    // arrange
    String path = "basic/workspaces/foo-test/my-git-repo";
    // act
    IdeContext context = newContext(path);
    // assert
    assertThat(context.env().getWorkspaceName()).isEqualTo("foo-test");
    assertThat(IdeVariables.DOCKER_EDITION.get(context)).isEqualTo("docker");
    EnvironmentVariables variables = context.env().getVariables();
    assertThat(variables.get("FOO")).isEqualTo("foo-bar-some-${UNDEFINED}");
    assertLogMessage(context, IdeLogLevel.WARNING,
        "Undefined variable UNDEFINED in 'SOME=some-${UNDEFINED}' for root 'FOO=foo-${BAR}'");
    assertThat(context.env().getIdeHome().resolve("readme")).hasContent("this is the IDE_HOME directory");
    assertThat(context.env().getIdeRoot().resolve("readme")).hasContent("this is the IDE_ROOT directory");
    assertThat(context.env().getUserHome().resolve("readme")).hasContent("this is the users HOME directory");
    assertThat(variables.getPath("M2_REPO")).isEqualTo(context.env().getUserHome().resolve(".m2/repository"));
    assertThat(context.env().getDownloadCache().resolve("readme")).hasContent("this is the download cache");
    assertThat(context.env().getDownloadMetadata().resolve("readme")).hasContent("this is the download metadata");
    assertThat(context.env().getToolRepository().resolve("readme")).hasContent("this is the tool repository");
    assertThat(context.env().getWorkspacePath().resolve("readme"))
        .hasContent("this is the foo-test workspace of basic");
    assertThat(variables.getType()).isSameAs(EnvironmentVariablesType.RESOLVED);
    assertThat(variables.getByType(EnvironmentVariablesType.RESOLVED)).isSameAs(variables);
    EnvironmentVariables v1 = variables.getParent();
    assertThat(v1.getType()).isSameAs(EnvironmentVariablesType.CONF);
    assertThat(variables.getByType(EnvironmentVariablesType.CONF)).isSameAs(v1);
    EnvironmentVariables v2 = v1.getParent();
    assertThat(v2.getType()).isSameAs(EnvironmentVariablesType.WORKSPACE);
    assertThat(variables.getByType(EnvironmentVariablesType.WORKSPACE)).isSameAs(v2);
    EnvironmentVariables v3 = v2.getParent();
    assertThat(v3.getType()).isSameAs(EnvironmentVariablesType.SETTINGS);
    assertThat(variables.getByType(EnvironmentVariablesType.SETTINGS)).isSameAs(v3);
    EnvironmentVariables v4 = v3.getParent();
    assertThat(v4.getType()).isSameAs(EnvironmentVariablesType.USER);
    assertThat(variables.getByType(EnvironmentVariablesType.USER)).isSameAs(v4);
    EnvironmentVariables v5 = v4.getParent();
    assertThat(v5.getType()).isSameAs(EnvironmentVariablesType.SYSTEM);
    assertThat(variables.getByType(EnvironmentVariablesType.SYSTEM)).isSameAs(v5);
    assertThat(v5.getParent()).isNull();
  }

}
