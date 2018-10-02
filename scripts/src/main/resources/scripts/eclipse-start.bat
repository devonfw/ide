@echo off

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

cd %ECLIPSE_HOME%
start /min eclipse.exe -data %WORKSPACE_PATH% -keyring "%USERPROFILE%\.eclipse\.keyring" %ECLIPSE_OPT%
