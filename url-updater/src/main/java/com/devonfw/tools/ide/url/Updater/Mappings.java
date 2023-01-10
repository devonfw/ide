package com.devonfw.tools.ide.url.Updater;

import java.util.ArrayList;
import java.util.HashMap;


public class Mappings {


    public Mappings(HashMap<OSType, String> oses, HashMap<String, String> architectures, HashMap<OSType, String> extensions) {
        this.releases.add(""); //Mandatory
        this.architectures.put("x64", "x64"); //Mandatory
        this.architectures = architectures;
        this.oses = oses;
        this.extensions = extensions;
    }
    public Mappings(){
        this.releases.add("");
        this.architectures.put("x64", "x64"); //Mandatory
        this.oses.put(OSType.WINDOWS, "");
        this.oses.put(OSType.LINUX, "");
        this.oses.put(OSType.MAC, "");
    }

    public Mappings(HashMap<OSType, String> oses) {
        this.releases.add("");//Mandatory
        this.architectures.put("x64", "x64");
        this.oses = oses;
    }

    public HashMap<OSType, String> oses = new HashMap<>();

    public HashMap<String, String> architectures = new HashMap<>();
    public HashMap<OSType, String> extensions = new HashMap<>();
    public ArrayList<String> releases = new ArrayList<>();

}
