@echo off
rem *******************************************************************************
rem These environment variables are part of devon-ide
rem Modifications will be overwritten on updates
rem To customize variables there are the following options:
rem 1. Project specific settings can be configured in 
rem    conf/variables.bat
rem 2. User specific settings can be configured in
rem    conf/variables-customized
rem *******************************************************************************

set SETTINGS_PATH=%CD%\settings

# Java
set JAVA_HOME=%CD%/software/java

rem Maven
rem set M2_REPO=%USERPROFILE%/.m2/repository
set M2_REPO=%CD%\conf\.m2\repository
set MAVEN_HOME=%CD%/software/maven

rem Eclipse
set ECLIPSE_VMARGS=-Xms128M -Xmx768M -XX:MaxPermSize=256M

