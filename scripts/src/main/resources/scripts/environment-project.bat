rem This batch is not supposed to be called manually
@echo off

call variables.bat
if exist variables-customized.bat (
  call variables-customized.bat
)
if "%WORKSPACE%" == "" (
  set WORKSPACE=%MAIN_BRANCH%
)

if "%WORKSPACE_VS%" == "" (
  set WORKSPACE_VS=%WORKSPACESVS_PATH%
)

set WORKSPACE_PLUGINS_PATH=%WORKSPACES_PATH%\%WORKSPACE%\.metadata\.plugins
set SETTINGS_PATH=%WORKSPACES_PATH%\%MAIN_BRANCH%\%SETTINGS_REL_PATH%
set ECLIPSE_TEMPLATES_PATH=%SETTINGS_PATH%\%ECLIPSE_TEMPLATES_REL_PATH%
rem absolute workspace path
set WORKSPACE_PATH=%CD%\%WORKSPACES_PATH%\%WORKSPACE%

if exist "%SETTINGS_PATH%\ide-properties.bat" (
  call "%SETTINGS_PATH%\ide-properties.bat"
)

set OASP4J_IDE_VERSION=1.4.0-SNAPSHOT
set ECLIPSE_CONFIGURATOR=oasp4j-ide-eclipse-configurator-1.4.0-SNAPSHOT.jar

rem ********************************************************************************
rem Java
set JAVA_HOME=%SOFTWARE_PATH%\java
rem set JAVA_OPTS=-Dhttp.proxyHost=myproxy.com -Dhttp.proxyPort=8080

rem ********************************************************************************
rem Maven

set M2_HOME=%SOFTWARE_PATH%\maven
set M2_CONF=%CD%\%CONF_PATH%\.m2\settings.xml

set MAVEN_OPTS=-Xmx512m -Duser.home=%CD%\%CONF_PATH%
set MAVEN_HOME=%M2_HOME%

rem ********************************************************************************
rem VSCODE
set VS_CODE=%SOFTWARE_PATH%\vscode\bin
set VS_CODE_HOME=%SOFTWARE_PATH%\vscode


rem ********************************************************************************
rem Eclipse
set ECLIPSE_HOME=%SOFTWARE_PATH%\eclipse
set ECLIPSE_OPT=-vm %JAVA_HOME%\bin\javaw -showlocation %WORKSPACE% -vmargs %ECLIPSE_VMARGS%

rem ********************************************************************************
rem Path
setlocal ENABLEDELAYEDEXPANSION
for /f "delims=" %%i in ('dir /a:d /b "%SOFTWARE_PATH%\*.*"') do (
  if exist "%SOFTWARE_PATH%\%%i\bin" (
    set "IDE_PATH=%SOFTWARE_PATH%\%%i\bin;!IDE_PATH!"
  ) else (
    set "IDE_PATH=%SOFTWARE_PATH%\%%i;!IDE_PATH!"
  )

  if exist "%SOFTWARE_PATH%\%%i\ide-config.bat" (
    call "%SOFTWARE_PATH%\%%i\ide-config.bat"
  )
)
(
  endlocal
  set "PATH=%IDE_PATH%%PATH%"
)
rem ********************************************************************************
rem Node.JS support
if exist "%SOFTWARE_PATH%\nodejs" (
  if not exist "%APPDATA%\npm" (
    md "%APPDATA%\npm";
  )
  set "PATH=%PATH%;%APPDATA%\npm"
)

echo IDE environment has been initialized.
