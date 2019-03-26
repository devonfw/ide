rem This batch is not supposed to be called manually
@echo off

set DEVON_IDE_HOME=%CD%
call "%DEVON_IDE_HOME%\scripts\variables.bat"
if exist "%DEVON_IDE_HOME%\variables.bat" (
  call "%DEVON_IDE_HOME%\variables.bat"
)
rem copy defaults
if not exist "%DEVON_IDE_HOME%\conf" (
  md "%DEVON_IDE_HOME%\conf"
)
if exist "%SETTINGS_PATH%" (
  if exist "%SETTINGS_PATH%\devon\variables.bat" (
    call "%SETTINGS_PATH%\devon\variables.bat"
  ) else (
    echo:
    echo *** ATTENTION ***
    echo Your devon-ide settings at %SETTINGS_PATH% are missing project-specific variables.
    echo Please create this file at %SETTINGS_PATH%\devon\variables.bat
    echo You can get a template from here:
    echo https://github.com/devonfw/devon-ide/blob/master/settings/src/main/settings/devon/variables.bat
  )
  if not exist "%DEVON_IDE_HOME%\conf\variables.bat" (
    if exist "%SETTINGS_PATH%\devon\conf\variables.bat" (
      copy "%SETTINGS_PATH%\devon\conf\variables.bat" "%DEVON_IDE_HOME%\conf\"
    ) else (
      echo:
      echo *** ATTENTION ***
      echo Your devon-ide settings at %SETTINGS_PATH% are missing a user-specific variables template.
      echo Please create this file at %SETTINGS_PATH%\devon\conf\variables.bat
      echo You can get a template from here:
      echo https://github.com/devonfw/devon-ide/blob/master/settings/src/main/settings/devon/conf/variables.bat
    )
  )
) else (
  echo:
  echo *** ATTENTION ***
  echo Your devon-ide is missing the settings at %SETTINGS_PATH%
  echo Please run the following command to complete your IDE setup:
  echo devon ide setup [^<settings-url^>]
)
if "%WORKSPACE%" == "" (
  set WORKSPACE=main
)
set "WORKSPACE_PATH=%CD%\workspaces\%WORKSPACE%"
if not exist "%WORKSPACE_PATH%" (
  if "%WORKSPACE%" == "main" (
    echo Creating main workspace directory
    md "%WORKSPACE_PATH%"
  ) else (
    echo WARNING: Worksapce %WORKSPACE% does not exist
  )
)

rem setup path
if not defined DEVON_OLD_PATH (
  set "DEVON_OLD_PATH=%PATH%"
)
set "SOFTWARE_PATH=%CD%\software"
if not exist "%SOFTWARE_PATH%" (
  echo:
  echo *** ATTENTION ***
  echo Your devon-ide is missing the software at %SOFTWARE_PATH%
  echo Please run the following command to complete your IDE setup:
  echo devon ide setup [^<settings-url^>]
  goto :variables
)
setlocal EnableDelayedExpansion
for /f "delims=" %%i in ('dir /a:d /b "%SOFTWARE_PATH%\*.*"') do (
  if exist "%SOFTWARE_PATH%\%%i\bin" (
    set "IDE_PATH=%SOFTWARE_PATH%\%%i\bin;!IDE_PATH!"
  ) else (
    set "IDE_PATH=%SOFTWARE_PATH%\%%i;!IDE_PATH!"
  )
  rem Load custom configuration of software
  if exist "%SOFTWARE_PATH%\%%i\ide-config.bat" (
    call "%SOFTWARE_PATH%\%%i\ide-config.bat"
  )
)
(
  endlocal
  set "PATH=%IDE_PATH%%DEVON_OLD_PATH%"
)

rem node.js support
if exist "%SOFTWARE_PATH%\nodejs" (
  if not exist "%APPDATA%\npm" (
    md "%APPDATA%\npm";
  )
  set "PATH=%PATH%;%APPDATA%\npm"
)

:variables
rem load user settings late so variables like M2_REPO can be overriden
if exist "%DEVON_IDE_HOME%\conf\variables.bat" (
  call "%DEVON_IDE_HOME%\conf\variables.bat"
)
