package com.devonfw.tools.ide.commandlet.gh;

import com.devonfw.tools.ide.commandlet.ToolCommandlet;

import picocli.CommandLine;

@CommandLine.Command(name = "gh",
        description = "This is gh commandlet"
)
public class Gh extends ToolCommandlet {
    @Override
    protected String getTool() {
        return "gh";
    }

    @Override
    protected void startTool() {

    }

}
