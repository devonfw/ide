package com.devonfw.tools.ide.commandlet.java;

import com.devonfw.tools.ide.commandlet.ToolCommandlet;

import picocli.CommandLine;

/**
 * {@link ToolCommandlet} for Java (Java Virtual Machine and Java Development Kit).
 */
@CommandLine.Command(name = "java", description = "This is java commandlet")
public class Java extends ToolCommandlet {

  @Override
  protected String getTool() {

    return "java";
  }

}
