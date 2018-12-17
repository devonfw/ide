@echo off
rem *******************************************************************************
rem These environment variables may be customized by the user.
rem To do so, create a file with the name variables-customized.bat
rem and perform your changes there so they do not get lost if you update. 
rem Whenever variables-customized.bat is present it will override the property values defined here.
rem *******************************************************************************

rem *******************************************************************************
rem internal generator paths
rem set MAIN_BRANCH=trunk
set MAIN_BRANCH=main
set SCRIPTS_PATH=scripts
set CONF_PATH=conf
set WORKSPACES_PATH=workspaces
set WORKSPACESVS_PATH=workspaces_vs
rem set SETTINGS_REL_PATH=settings
set SETTINGS_REL_PATH=development\settings
rem absolute software path
if "%CD:~-1%" == "/" (
  set SOFTWARE_PATH=%CD%software
) else (
  if "%CD:~-1%" == "\" (
    set SOFTWARE_PATH=%CD%software
  ) else (
    set SOFTWARE_PATH=%CD%/software
  )
)

set DVCMDER_INSTALL_DIR=%SOFTWARE_PATH%\cmder\Cmder.exe
set PYTHON3=%SOFTWARE_PATH%\python3\python.exe
rem *******************************************************************************
rem by default the OASP4J IDE configures its own repository per instance.
set M2_REPO=%CD%\%CONF_PATH%\.m2\repository
REM set M2_REPO=%USERPROFILE%/.m2/repository
set ECLIPSE_VMARGS=-Xms128M -Xmx768M -XX:MaxPermSize=256M

rem subversion
set UPDATE_SUBVERSION_CONFIG=false
set UPDATE_SUBVERSION_SERVERS=false
rem *******************************************************************************