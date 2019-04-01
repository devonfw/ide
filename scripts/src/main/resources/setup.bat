@echo off

pushd %~dp0

call scripts\devon.bat ide setup
call regedit.exe /S system\windows\cmd\devon-cmd.reg

pause

popd

cmd