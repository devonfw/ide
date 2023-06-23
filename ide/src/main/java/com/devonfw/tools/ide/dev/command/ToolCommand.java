package com.devonfw.tools.ide.dev.command;

import com.devonfw.tools.ide.dev.environment.Environment;
import com.devonfw.tools.ide.dev.functions.Functions;
import picocli.CommandLine;

import java.util.Locale;

public abstract class ToolCommand extends AbstractCommand {
    private final String DEVON_DOWNLOADS_DIR = System.getProperty("user.home") + "/Downloads/devon/software/";

    protected abstract String getTool();
    protected abstract void startTool();

    @CommandLine.Option(names = "setup", description = "this option is used to setup the tool")
    private boolean setup;

    @CommandLine.Option(names = {"version", "versions", "v"}, description = "this option is used handle versions for the tool and needs to be followed by other options")
    private boolean version;

    @CommandLine.Option(names = {"list", "ls"}, description = "this option is used in combination with the <versions> option to list the versions")
    private boolean list;

    @CommandLine.ArgGroup(exclusive = false)
    SetVersion setVersion;

    static class SetVersion{
        @CommandLine.Option(names = {"set", "sv"}, description = "this option is used in combination with the <versions> option to set a certain version")
        boolean setVersion;

        @CommandLine.Option(names = {"-v", "--version"}, required = true, description = "the version to set")
        String version;
    }

    @Override
    public void run() {
        if (setup){
            try {
                setup();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (version && list){
            listVersions();
        }
        else if (version && setVersion.setVersion){
            if(setVersion.version.isEmpty()){
                System.err.println("Please provide a correct version.");
                System.exit(1);
            }
            setVersion();
        }
        else {
            startTool();
        }
    }

    protected String getEdition(){
        String editionVar = getTool().toUpperCase(Locale.ROOT) +  "_EDITION";
        String edition = Environment.get().get(editionVar);
        return edition;
    }

    protected String getVersion(){
        String versionVar = getTool().toUpperCase(Locale.ROOT) +  "_VERSION";
        String version = Environment.get().get(versionVar);
        return version;
    }
    protected void setup() {
        Functions.setup("", DEVON_DOWNLOADS_DIR, getTool(), getVersion(), "", "", "", "", "", "");
    }

    protected void listVersions(){
        Functions.listVersions(getTool(), getEdition());
    }

    protected void setVersion(){
        Functions.setSoftwareVersion(getTool(), setVersion.version);
    }

}
