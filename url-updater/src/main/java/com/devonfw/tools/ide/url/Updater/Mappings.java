package com.devonfw.tools.ide.url.Updater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Mappings {


    public Mappings(HashMap<OSTypes, String> oses, HashMap<String, String> architectures, HashMap<OSTypes, String> extensions) {
        this.releases.add(""); //Mandatory
        this.architectures = architectures;
        this.architectures.put("x64", "x64"); //Mandatory
        this.oses = oses;
        this.extensions = extensions;
    }
    public Mappings(){
        this.releases.add("");
        this.architectures.put("x64", "x64");
        this.oses.put(OSTypes.WINDOWS, "");
        this.oses.put(OSTypes.LINUX, "");
        this.oses.put(OSTypes.MAC, "");
    }

    public Mappings(HashMap<OSTypes, String> oses) {
        this.releases.add("");//Mandatory
        this.architectures.put("x64", "x64");
        this.oses = oses;
    }

    public HashMap<OSTypes, String> oses = new HashMap<>();

    public HashMap<String, String> architectures = new HashMap<>();
    public HashMap<OSTypes, String> extensions = new HashMap<>();
    public ArrayList<String> releases = new ArrayList<>();

}
