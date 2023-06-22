package com.devonfw.tools.ide.dev.tool;

import com.devonfw.tools.ide.dev.command.ToolCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "gh",
        description = "This is gh commandlet"
)
public class Gh extends ToolCommand {
    @Override
    protected String getTool() {
        return "gh";
    }

    @Override
    protected void startTool() {

    }

}
