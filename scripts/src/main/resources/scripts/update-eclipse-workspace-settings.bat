@echo off

pushd %~dp0
rem go to IDE home folder.
cd..

rem ********************************************************************************
rem argument1 = workspace name (defaults to %MAIN_BRANCH%)
if "%1" == "" (
	set WORKSPACE=%MAIN_BRANCH%
	set MODE=-c
	goto :saveChanges
)
if "%1" == "--new" (
	set MODE=-cn
	if "%2" == "" (
		set WORKSPACE=%MAIN_BRANCH%
	) else (
		set WORKSPACE=%2
	)
) else (
	set WORKSPACE=%1
	set MODE=-c
)

:saveChanges
call scripts\environment-project.bat

if not exist %WORKSPACE_PLUGINS_PATH% (
	echo Could not find workspace %WORKSPACE%
	echo Update aborted
	goto :end
)

rem ********************************************************************************
rem In order to run this jar requires the following environment variables:
rem WORKSPACE_PLUGINS_PATH, ECLIPSE_TEMPLATES_PATH and REPLACEMENT_PATTERNS_PATH
java -jar %SCRIPTS_PATH%\%ECLIPSE_CONFIGURATOR% %MODE%

:end
popd
pause
