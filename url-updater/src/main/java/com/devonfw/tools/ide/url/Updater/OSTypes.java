package com.devonfw.tools.ide.url.Updater;

public enum OSTypes {
    WINDOWS,MAC,LINUX;
    @Override
    public String toString(){
        //Return the name of the enum in lowercase except for first letter
        return this.name().charAt(0)+ this.name().substring(1).toLowerCase();
    }
}