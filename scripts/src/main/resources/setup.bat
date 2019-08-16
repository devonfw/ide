@echo off

pushd %~dp0
echo Setting up your devon-ide in %CD%
call scripts\devon.bat ide setup
reg import system/windows/cmd/devon-cmd.reg
reg import system/windows/power-shell/devon-power-shell.reg

echo Setup of devon-ide completed
pause

popd
