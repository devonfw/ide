@echo off

pushd %~dp0
rem go to IDE home folder.
cd..

rem ********************************************************************************
rem argument1 = workspace name (defaults to %MAIN_BRANCH%)
if "%1" == "" (
	set WORKSPACE=%MAIN_BRANCH%
	set MODE=-i
	goto :saveChanges
)
if "%1" == "--new" (
	set MODE=-x
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

rem ********************************************************************************
set ECLIPSE_TEMPLATES_PATH=%SETTINGS_PATH%\eclipse\workspace
java -jar %SCRIPTS_PATH%\%IDE_CONFIGURATOR% -t "%ECLIPSE_TEMPLATES_PATH%" -w "%WORKSPACE_PATH%" -v "%REPLACEMENT_PATTERNS_PATH%" %MODE%

:end
popd
pause
