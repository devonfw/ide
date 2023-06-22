package com.devonfw.tools.ide.dev;

import com.devonfw.tools.ide.dev.environment.Environment;
import com.devonfw.tools.ide.dev.tool.Gh;
import com.devonfw.tools.ide.dev.tool.Java;
import picocli.CommandLine;
import java.util.concurrent.Callable;


@CommandLine.Command(name = "devon",
        version = "1.0.0",
        description = "This is a Devon CLI",
        header = "Devon CLI",
        optionListHeading = "%nOptions are:%n",
        commandListHeading = "%nSubCommands are: %n",
        subcommands = {
                Java.class,
                Gh.class,
                Environment.class
        }
)
public class Devon implements Callable<Integer> {
    final Integer SUCCESS = 0;

    public static void main(String... args) {
        int exitStatus = new CommandLine(new Devon())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitStatus);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("[devon] Welcome to Devon-IDE");
        return SUCCESS;
    }

}