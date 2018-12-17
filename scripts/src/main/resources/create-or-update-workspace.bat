@echo off

rem ********************************************************************************
rem argument1 = workspace name (defaults to %MAIN_BRANCH%)
if "%1" NEQ "" (
  set WORKSPACE=%1
)
call scripts\environment-project.bat

rem FYI: stores the name of the current directory for use by the popd command
rem		 before chaining the current directory to the specified directory.
rem		 %~dp0 = the directory in which this batch file is located
pushd %~dp0

rem ********************************************************************************
rem if SOFTWARE_PATH does not exist, do nothing
if not exist "%SOFTWARE_PATH%" (
	echo Could not find folder "%SOFTWARE_PATH%"
	echo If you want to change its name see the variables.bat
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem if workspaces does not exist, do nothing
if not exist %WORKSPACES_PATH% (
	echo Could not find folder "%WORKSPACES_PATH%"
	echo If you want to change its name see the variables.bat
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem if the current workspace does not exist, do nothing
if not exist "%WORKSPACE_PATH%" (
	echo Could not find workspace %WORKSPACE_PATH%
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem if settings path does not exist, do nothing
if not exist %SETTINGS_PATH% (
	echo Could not find folder "%SETTINGS_PATH%"
	echo If you want to change its name see the variables.bat
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem copy maven settings to .m2 folder if not exist
if not exist conf\.m2 mkdir conf\.m2
if not exist conf\.m2\settings.xml (
 	copy %SETTINGS_PATH%\maven\settings.xml conf\.m2\settings.xml >nul
	echo Copied %SETTINGS_PATH%\maven\settings.xml to conf\.m2\settings.xml
)

rem ********************************************************************************
rem copy version for ide folder if not exist
if not exist conf\.m2\settings.json (
 	copy %SETTINGS_PATH%\version\settings.json conf\settings.json >nul
	echo Copied %SETTINGS_PATH%\version\settings.json to conf\settings.json
)

rem ********************************************************************************
rem copy subversion config and server to the users subversion folder, overwrite if exist.
rem only if UPDATE_SUBVERSION_CONFIG or UPDATE_SUBVERSION_SERVERS is set "true"

if "%UPDATE_SUBVERSION_CONFIG%" NEQ "true" if "%UPDATE_SUBVERSION_SERVERS%" NEQ true goto :subversion_end

if not exist %APPDATA%\Subversion (
	echo Could not find subversion folder: %APPDATA%\Subversion
	echo Skipping copy process
	goto :subversion_end
)
if "%UPDATE_SUBVERSION_CONFIG%"=="true" (
	copy %SETTINGS_PATH%\subversion\config %APPDATA%\Subversion\config >nul
	echo Copied and replaced %SETTINGS_PATH%\subversion\config to %APPDATA%\Subversion\config
)

if "%UPDATE_SUBVERSION_SERVERS%"=="true" (
	copy %SETTINGS_PATH%\subversion\servers %APPDATA%\Subversion\servers >nul
	echo Copied and replaced %SETTINGS_PATH%\subversion\servers %APPDATA%\Subversion\servers
)

:subversion_end

rem ********************************************************************************
rem ECLIPSE_TEMPLATES_PATH is required in order for the eclipse configurator to work
set ECLIPSE_TEMPLATES_PATH=%SETTINGS_PATH%\eclipse\workspace
if not exist "%ECLIPSE_TEMPLATES_PATH%" (
	echo Could not find workspace at %ECLIPSE_TEMPLATES_PATH%
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem REPLACEMENT_PATTERNS_PATH is required in order for the eclipse configurator to work
if not exist "%REPLACEMENT_PATTERNS_PATH%" (
	echo Could not find variables at %REPLACEMENT_PATTERNS_PATH%
	echo Execution aborted
	goto :end
)

rem ********************************************************************************
rem copys/merges the *.prefs form %SETTINGS_PATH%\eclipse\workspace\...
rem to specified Eclipse workspace
rem ********************************************************************************
java -jar %SCRIPTS_PATH%\%IDE_CONFIGURATOR% -t "%ECLIPSE_TEMPLATES_PATH%" -w "%WORKSPACE_PATH%" -v "%REPLACEMENT_PATTERNS_PATH%" -u
if %ERRORLEVEL% NEQ 0 (
  echo "Configurator has failed! Please check the logs."
  exit /B -1
)

echo Eclipse preferences for workspace: "%WORKSPACE%" have been created/updated

rem ********************************************************************************
rem creates batch to run eclipse with the specified workspace
call %SCRIPTS_PATH%\create-eclipse-startup-bat.bat

rem FYI: changes the current directory to the directory stored by the pushd command
popd

echo Finished creating/updating workspace: "%WORKSPACE%"
echo:

:end
if "%2" == "" (
	pause
)