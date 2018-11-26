@echo off

pushd %~dp0

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

popd

powershell