package com.devonfw.tools.ide.commandlet.mvn;

import com.devonfw.tools.ide.commandlet.ToolCommandlet;
import com.devonfw.tools.ide.commandlet.java.Java;

import picocli.CommandLine;

/**
 * {@link ToolCommandlet} for <a href="https://maven.apache.org/">maven</a>.
 */
@CommandLine.Command(name = "mvn", description = "Setup and use maven")
public class Mvn extends ToolCommandlet {

  @Override
  protected String getTool() {

    return "mvn";
  }

  @Override
  public void install(boolean silent) {

    context().getCommandlet(Java.class).install();
    super.install(silent);
  }

}
