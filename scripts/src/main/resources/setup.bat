@echo off

pushd %~dp0

call scripts\devon.bat ide setup
call regedit.exe /s system/windows/cmd/devon-cmd.reg
call regedit.exe /s system/windows/power-shell/devon-power-shell.reg

echo Setup of devon-ide completed
pause

popd
