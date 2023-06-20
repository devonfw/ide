package com.devonfw.tools.ide.dev.environment;

import com.devonfw.tools.ide.dev.command.EnvCommand;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@CommandLine.Command(name = "env",
        description = "This command prints out the devonfw-ide environment variables"
)
public final class Environment extends EnvCommand {

    @CommandLine.Option(names = "var", description = "this option is used to print out a certain environment variable")
    private String variable;

    private static final Environment INSTANCE = new Environment();
    private final Properties properties;

    private Environment() {
        super();
        this.properties = new Properties();
        this.properties.putAll(System.getProperties());
        this.properties.putAll(System.getenv());
        final String DEVON_IDE_HOME;
        try {
            DEVON_IDE_HOME = findIDEHome();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.properties.put("DEVON_IDE_HOME", DEVON_IDE_HOME);
        // load all devon.properties in the order defined by
        // https://github.com/devonfw/ide/blob/master/documentation/configuration.asciidoc
        List<String> propertiesPaths = new ArrayList<>();
        propertiesPaths.add(System.getProperty("user.home")+"/devon.properties");
        propertiesPaths.add(DEVON_IDE_HOME+"/scripts/devon.properties");
        propertiesPaths.add(DEVON_IDE_HOME+"/settings/devon.properties");
        propertiesPaths.add(DEVON_IDE_HOME+"/conf/devon.properties");
        propertiesPaths.add(DEVON_IDE_HOME+"/settings/projects/*.properties");
        for (String propertiesPath : propertiesPaths) {
            File file = new File(propertiesPath);
            if (file.exists()) {
                try{
                    this.properties.load(new FileInputStream(propertiesPath));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value.contains("${DEVON_IDE_HOME}")){
                value = value.replace("${DEVON_IDE_HOME}", DEVON_IDE_HOME);
                this.properties.replace(key, value);
            }
        }
    }

    /**
     * @param name the name of the environment variable to get.
     * @return the value of the variable with the given {@code name}. Will be the empty string if no such variable is
     * defined.
     */
    public String get(String name) {
        return get(name, "");
    }

    /**
     * @param name the name of the environment variable to get.
     * @param defaultValue the value to return in case the requested variable is not defined.
     * @return the value of the variable with the given {@code name}.
     */

    public String get(String name, String defaultValue) {
        String value = this.properties.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static Environment get() {
        return INSTANCE;
    }
    private String findIDEHome() throws Exception {
        String devonIdeHome = "";
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        Path baseDirectory = currentDirectory;

        while (true) {
            if (Files.exists(currentDirectory.resolve("scripts/environment-project"))
                    && !currentDirectory.toString().endsWith("/scripts/src/main/resources")) {
                String restDir = baseDirectory.toString().substring(currentDirectory.toString().length());
                String workspace = "main";

                if (restDir.startsWith("/workspaces/")) {
                    restDir = restDir.substring(12);
                    restDir = restDir.substring(0, restDir.indexOf("/"));

                    if (!restDir.isEmpty()) {
                        Path wks = Paths.get(currentDirectory.toString(), "workspaces", restDir);
                        if (Files.isDirectory(wks)) {
                            workspace = restDir;
                        }
                    }
                }

                devonIdeHome = currentDirectory.toString();
                break;
            }
            try{
                Path linkDir = currentDirectory.toRealPath();
                if (!currentDirectory.equals(linkDir)) {
                    currentDirectory = linkDir;
                    baseDirectory = linkDir;
                } else {
                    currentDirectory = currentDirectory.getParent();
                    if (currentDirectory == null) {
                        System.out.println("You are not inside a devonfw-ide installation: " + System.getProperty("user.dir"));
                        break;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if(devonIdeHome.isEmpty() || devonIdeHome == null){
            throw new Exception("You are not inside a devonfw-ide installation!");
        }
        return devonIdeHome;
    }

    @Override
    protected void envCommand() {
        if (variable == null || variable.isEmpty()){
            for (String variableName : properties.stringPropertyNames()) {
                String variableValue = properties.getProperty(variableName);
                System.out.println(variableName + "=" + variableValue);
            }
        }
        else {
            String value = properties.getProperty(variable);
            if(value == null || value.isEmpty()){
                System.err.println("No such variable!");
                System.exit(1);
            }
            else{
                System.out.println(variable + "=" + value);
            }
        }
    }
}
