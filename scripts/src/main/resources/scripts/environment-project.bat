rem This batch is not supposed to be called manually
@echo off

call :load_properties "%DEVON_IDE_HOME%\scripts\devon.properties"
call :load_properties "%USERPROFILE%\devon.properties"
call :load_properties "%DEVON_IDE_HOME%\devon.properties"
call :load_properties "%SETTINGS_PATH%\devon.properties"
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
call :load_properties "%WORKSPACE_PATH%\devon.properties"

rem setup path
if not defined DEVON_OLD_PATH (
  set "DEVON_OLD_PATH=%PATH%"
)
set "SOFTWARE_PATH=%CD%\software"
if not exist "%SETTINGS_PATH%" (
  echo:
  echo *** ATTENTION ***
  echo Your devonfw-ide is missing the settings at %SETTINGS_PATH%
  echo Please run the following command to complete your IDE setup:
  echo devon ide setup [^<settings-url^>]
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
call :load_properties "%DEVON_IDE_HOME%\conf\devon.properties"
goto :eof

rem subroutine to load properties as environment variables with
rem %~1: path to properties file
:load_properties
if not exist "%~1" (
  goto :eof
)
for /F "eol=# delims== tokens=1,*" %%a in (%~1) do (
  if not "%%a" == "" (
    call :set_variable "%%a" "%%b"
  )
)
goto :eof

rem subroutine to set environment variable with
rem %~1: variable name
rem %~2: variable value (may contain ~ or ${var})
:set_variable
setlocal EnableDelayedExpansion
set "value=%~2"
rem replace ${var} variable syntax with windows %var% syntax
set "value=!value:${=%%!
set "value=!value:}=%%!
rem resolve ~ to user home (USERPROFILE)
if "!value:~0,1!" == "~" (
  set "value=%USERPROFILE%!value:~1!"
)
set "var=%~1"
rem remove potential export as this only makes sense on linux/mac
set "var=!var:export =!
(
  rem endlocal in () block to access local variable and "export" it
  endlocal
  rem use call in order to evaluate %var% inside value
  call set "%var%=%value%"
)
goto :eof