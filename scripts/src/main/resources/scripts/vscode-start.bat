@echo off

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

cd %VS_CODE_HOME%

if %WORKSPACE% == %WORKSPACESVS_PATH% (
	start /min Code.exe ..\..\%WORKSPACE%
) else (
	start /min Code.exe ..\..\%WORKSPACESVS_PATH%\%WORKSPACE%\ 
)