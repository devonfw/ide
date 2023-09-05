package com.devonfw.tools.ide.commandlet;

import java.util.Collection;

import com.devonfw.tools.ide.environment.VariableLine;

import picocli.CommandLine;

/**
 * {@link Commandlet} to print the environment variables.
 */
@CommandLine.Command(name = "env", description = "This command prints out the devonfw-ide environment variables")
public final class EnvironmentCommand extends Commandlet {

  @Override
  public void run() {

    Collection<VariableLine> variables = context().getVariables().collectVariables();
    for (VariableLine line : variables) {
      context().info(line.toString());
    }
  }
}
