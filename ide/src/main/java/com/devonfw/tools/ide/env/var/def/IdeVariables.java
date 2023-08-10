package com.devonfw.tools.ide.env.var.def;

import java.util.Collection;
import java.util.List;

import com.devonfw.tools.ide.env.Environment;

/**
 * Interface (mis)used to define all the available variables.
 */
public interface IdeVariables {

  /** {@link VariableDefinition} for {@link Environment#getIdeHome() IDE_HOME}. */
  VariableDefinitionPath IDE_HOME = new VariableDefinitionPath("IDE_HOME", "DEVON_IDE_HOME", c -> c.env().getIdeHome(),
      true);

  /** {@link VariableDefinition} for {@link Environment#getWorkspaceName() WORKSPACE}. */
  VariableDefinitionString WORKSPACE = new VariableDefinitionString("WORKSPACE", null, c -> c.env().getWorkspaceName(),
      true);

  /** {@link VariableDefinition} for {@link Environment#getWorkspacePath() WORKSPACE_PATH}. */
  VariableDefinitionPath WORKSPACE_PATH = new VariableDefinitionPath("WORKSPACE_PATH", null,
      c -> c.env().getWorkspacePath(), true);

  /** {@link VariableDefinition} for list of tools to install by default. */
  VariableDefinitionStringList IDE_TOOLS = new VariableDefinitionStringList("IDE_TOOLS", "DEVON_IDE_TOOLS");

  /** {@link VariableDefinition} for list of IDE tools to create start scripts for. */
  VariableDefinitionStringList CREATE_START_SCRIPTS = new VariableDefinitionStringList("CREATE_START_SCRIPTS",
      "DEVON_CREATE_START_SCRIPTS");

  /** {@link VariableDefinition} for version of maven (mvn). */
  VariableDefinitionVersion MVN_VERSION = new VariableDefinitionVersion("MVN_VERSION", "MAVEN_VERSION");

  /** {@link VariableDefinition} for minimum IDE product version. */
  // TODO define initial IDEasy version as default value
  VariableDefinitionVersion IDE_MIN_VERSION = new VariableDefinitionVersion("IDE_MIN_VERSION", "DEVON_IDE_MIN_VERSION");

  /** A {@link Collection} with all pre-defined {@link VariableDefinition}s. */
  Collection<VariableDefinition<?>> VARIABLES = List.of(IDE_HOME, WORKSPACE, WORKSPACE_PATH, IDE_TOOLS,
      CREATE_START_SCRIPTS, MVN_VERSION, IDE_MIN_VERSION);

}
