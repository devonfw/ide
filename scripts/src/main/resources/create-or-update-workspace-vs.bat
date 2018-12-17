@echo off

rem ********************************************************************************
rem argument1 = workspace name (defaults to %MAIN_BRANCH%)
if "%1" NEQ "" (
  set WORKSPACE_VS=%1
)

call scripts\environment-project.bat

rem FYI: stores the name of the current directory for use by the popd command
rem		 before chaining the current directory to the specified directory.
rem		 %~dp0 = the directory in which this batch file is located
pushd %~dp0


rem creates batch to run eclipse with the specified workspace

call scripts\create-vscode-startup-bat.bat

rem FYI: changes the current directory to the directory stored by the pushd command
popd

echo Finished creating/updating workspace: "%WORKSPACE_VS%"

echo:

:end
if "%2" == "" (
	pause
)