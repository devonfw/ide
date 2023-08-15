package com.devonfw.tools.ide.commandlet.java;

import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;

import com.devonfw.tools.ide.cli.functions.Functions;
import com.devonfw.tools.ide.commandlet.EnvironmentCommand;
import com.devonfw.tools.ide.commandlet.ToolCommandlet;

@CommandLine.Command(name = "java",
        description = "This is java commandlet"
)
public class Java extends ToolCommandlet {

    private static final String DEVON_IDE_HOME = EnvironmentCommand.get().get("DEVON_IDE_HOME");
    private static final String DEVON_SOFTWARE_DIR = DEVON_IDE_HOME + "/software/";
    @Override
    protected String getTool() {
        return "java";
    }

    /**
    The additional attributes added are:
     - arity: is set to 0..1 to make the parameter optional.
     - defaultValue: is set to an empty string, indicating that if the parameter is not provided, it will default to an empty string.
     - showDefaultValue: is used to display the default value in the usage help.
     **/
    @CommandLine.Parameters(description = "java cli options", arity = "0..1", defaultValue = "", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String option;
    @Override
    protected void startTool() {
        Path pathToBinFolder = Functions.searchFolder(DEVON_SOFTWARE_DIR+getTool(), "bin");
        if (pathToBinFolder != null){
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(getTool(), option);
            try {
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
