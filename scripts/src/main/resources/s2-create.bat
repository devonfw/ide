@echo off

rem **************************************************************
rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

rem **************************************************************
rem validating parameters
if [%1] == [] (
	echo [ERROR] Project-name parameter is missing
	goto :end
)

if NOT [%2] == [] (

	if [%3] == [] (
		echo [ERROR] Svn-user parameter is missing
		goto :end
	)

	if [%4] == [] (
		echo [ERROR] Svn-password parameter is missing
		goto :end
	)

)

rem **************************************************************
rem setting variables
set PRJ_NAME=%1
set REPOSITORY_URL=%2
set USER=%3
set PASS=%4

rem **************************************************************
echo *** CONFIGURATION PARAMETERS ***
echo Project-name: %PRJ_NAME%
echo Svn-repository: %REPOSITORY_URL%
echo Svn-user: %USER%
echo Svn-password: %PASS%
echo ********************************

rem **************************************************************
rem creating workspace and checking out the svn repository
if not exist workspaces\%PRJ_NAME% (
	echo [INFO] Creating project workspace...
	mkdir workspaces\%PRJ_NAME%
	cd workspaces\%PRJ_NAME%
	echo [INFO] Project workspace created successfuly.

	if NOT "%REPOSITORY_URL%" == "" (
		echo [INFO] Doing the checkout of the SVN repository...
		svn checkout %REPOSITORY_URL% --username %USER% --password %PASS% --non-interactive && ( 
			echo [INFO] The checkout has been done successfully.

		) || (
			echo [ERROR] The checkout could not be done.
			cd ..\..

			rem deleting the just created project folder to clean up the environment
			rd /s /q workspaces\%PRJ_NAME%

			EXIT /b 1

		)
		
	)
	
	cd ..\..
	echo [INFO] Updating workspace...
	call create-or-update-workspace.bat %PRJ_NAME% avoidPause
	echo [INFO] End.
) else (
	echo [ERROR] A project with the same name %PRJ_NAME% already exist in the workspaces folder.
	EXIT /b 1
)


:end
rem pause