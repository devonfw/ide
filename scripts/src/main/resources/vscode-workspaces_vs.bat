@echo off
pushd %~dp0
set WORKSPACE=workspaces_vs
call scripts\vscode-start.bat
popd
