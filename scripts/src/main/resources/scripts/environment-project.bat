rem This batch is not supposed to be called manually
@echo off

rem TODO remove old IDE entries from PATH 
call "scripts\variables.bat"
if exist "conf\variables.bat" (
  call "conf\variables.bat"
)
if exist "%SETTINGS_PATH%\ide-properties.bat" (
  call "%SETTINGS_PATH%\ide-properties.bat"
)
if "%WORKSPACE%" == "" (
  set WORKSPACE=main
)
set WORKSPACE_PATH=%CD%\workspaces\%WORKSPACE%
if not exist "%WORKSPACE_PATH%" (
  if "%WORKSPACE%" == "main" (
    echo Creating main workspace directory
    md "%WORKSPACE_PATH%"
  ) else (
    echo WARNING: Worksapce %WORKSPACE% does not exist
  )
)

rem copy defaults
if not exist "conf" (
  md "conf"
)
if exist "%SETTINGS_PATH%" (
  if not exist "conf\variables.bat" (
    if exist "%SETTINGS_PATH%\devon\variables.bat" (
      copy "%SETTINGS_PATH%\devon\variables.bat" "conf\"
	)
  )
  if not exist "conf\variables-customized.bat" (
    if exist "%SETTINGS_PATH%\devon\variables-customized.bat" (
      copy "%SETTINGS_PATH%\devon\variables-customized.bat" "conf\"
	)
  )
) else (
  echo
  echo *** ATTENTION ***
  echo Your devon-ide is missing the settings at %SETTINGS_PATH%
  echo Please run the following command to complete your IDE setup:
  echo devon ide setup
)

rem setup path
set SOFTWARE_PATH=%CD%\software
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
  set "PATH=%IDE_PATH%%PATH%"
)

rem node.js support
if exist "%SOFTWARE_PATH%\nodejs" (
  if not exist "%APPDATA%\npm" (
    md "%APPDATA%\npm";
  )
  set "PATH=%PATH%;%APPDATA%\npm"
)

if exist conf\variables-customized.bat (
  call conf\variables-customized.bat
)
