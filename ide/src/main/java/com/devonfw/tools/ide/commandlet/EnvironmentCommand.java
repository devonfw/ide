package com.devonfw.tools.ide.commandlet;

import java.util.HashMap;
import java.util.Map;

import picocli.CommandLine;

@CommandLine.Command(name = "env", description = "This command prints out the devonfw-ide environment variables")
public final class EnvironmentCommand extends Commandlet {

  private EnvironmentCommand() {

    super();
  }

  @Override
  public void run() {

    Map<String, String> map = new HashMap<>();
    context().env().getVariables().collectVariables(map);
    for (String line : map.values()) {
      context().info(line);
    }
  }
}
