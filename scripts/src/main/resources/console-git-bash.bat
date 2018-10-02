@echo off

pushd %~dp0

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

popd

if EXIST "%SYSTEMDRIVE%\Program Files\Git\bin\sh.exe". (
	"%SYSTEMDRIVE%\Program Files\Git\bin\sh.exe" --login
) else (
	if EXIST "%SYSTEMDRIVE%\Program Files (x86)\Git\bin\sh.exe". (
		"%SYSTEMDRIVE%\Program Files (x86)\Git\bin\sh.exe" --login
	)
)