@echo off

pushd %~dp0

call scripts\environment-project.bat

set WORKSPACES=%CD%\%WORKSPACES_PATH%
set WORKSPACES_VS=%CD%\%WORKSPACESVS_PATH%
set UPDATING_ALL_WORKSPACES=true

if not exist "%WORKSPACES%" (
	echo Can not find directory: %WORKSPACES%
	echo Execution aborted
	goto :end
)

for /f "delims=" %%i in ('dir /a:d /b "%WORKSPACES%\*.*"') do call create-or-update-workspace.bat %%i noPause

for /f "delims=" %%i in ('dir /a:d /b "%WORKSPACES_VS%\*.*"') do call create-or-update-workspace-vs.bat %%i noPause

set WORKSPACE_VS=%WORKSPACESVS_PATH%

call scripts\create-vscode-startup-bat.bat

popd

echo Finished updating all workspaces

:end
pause