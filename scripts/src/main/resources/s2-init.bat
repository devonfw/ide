@echo off
echo starting...

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

rem **************************************************************
rem validating parameters
if [%1] == [] (
	echo [ERROR] userName parameter is missing
	goto :end
)

if [%2] == [] (
	echo [ERROR] Password parameter is missing
	goto :end
)

if [%3] == [] (
	echo [ERROR] Engagement-name parameter is missing
	goto :end
)

if [%4] == [] (
	set ciaas="false"
	goto :script
)

if "%4" == "ciaas" (
	set ciaas="true"
	goto :script
)

echo "%4 not valid as value. Use 'ciaas' to configure the settings.xml file for Continuous Integration"
goto :end

:script
rem launches the s2 repository activation script
node scripts\s2-repository-activation.js %1 %2 %3 %ciaas% && ( 
		echo [INFO] init process ended successfully.
		EXIT /b 0
	) || (
		echo [ERROR] init process ended with errors.
		EXIT /b 1
	)

:end
rem pause