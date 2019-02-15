@echo off

pushd %CD%

:iterate_backwards
if not exist scripts\environment-project.bat (
	rem search for "scripts/environment-project.bat"
	if "%CD%"=="%CD:~0,3%" (
		REM echo Directory is not in a OASP4J IDE
		goto :end
	)
	cd..
	goto :iterate_backwards
) else (
	call scripts\environment-project.bat
)

:end
popd