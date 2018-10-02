@echo off

pushd %~dp0

call scripts\environment-project.bat

set WORKSPACES=%CD%\%WORKSPACES_PATH%
set UPDATING_ALL_WORKSPACES=true

if not exist "%WORKSPACES%" (
	echo Can not find directory: %WORKSPACES%
	echo Execution aborted
	goto :end
)

for /f "delims=" %%i in ('dir /a:d /b "%WORKSPACES%\*.*"') do call create-or-update-workspace.bat %%i noPause

popd

echo Finished updating all workspaces

:end
pause