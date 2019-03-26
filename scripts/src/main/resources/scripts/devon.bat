@echo off
if "%1%" == "-v" (
  goto :printVersion
)
if "%1%" == "--version" (
  goto :printVersion
)
pushd %CD%

rem Auto-install oursevles...
if not exist "%USERPROFILE%\scripts" (
  md "%USERPROFILE%\scripts"
)
if not exist "%USERPROFILE%\scripts\devon.bat" (
  echo Copying devon CLI script to your home directory...
  copy "%~f0" "%USERPROFILE%\scripts\devon.bat"
  for %%f in (devon.bat) do set "path=%%~$PATH:f"
  if not defined path (
    echo Adding %USERPROFILE%\scripts to your users system PATH
    for /F "tokens=2* delims= " %%f IN ('reg query HKCU\Environment /v PATH ^| findstr /i path') do set USER_PATH=%%g
    if "%USER_PATH:~-1,1%" == ";" (
      set "USER_PATH=%USER_PATH:~0,-1%"
    )
    setx PATH "%USER_PATH%;%%USERPROFILE%%\scripts"
    if "%PATH:~-1,1%" == ";" (
      set "PATH=%PATH%%USERPROFILE%\scripts"
    ) else (
      set "PATH=%PATH%;%USERPROFILE%\scripts"
    )
  )
  echo The devon CLI script has been installed to your system.
  echo Now in any new command shell, you can call devon to setup your IDE enviromennt variables.
  echo You can also provide arguments to devon for advanced usage, e.g. try calling 'devon help'
)

set WORKSPACE=""
:iterate_backwards
if exist scripts\environment-project.bat (
  call scripts\environment-project.bat
  echo devon-ide environment variables have been set for %CD%
  popd
  goto :cli
)
if "%CD%" == "%CD:~0,3%" (
  popd
  echo You are not inside a devon IDE installation: %CD%
  goto :cli
)
set last_folder=%folder%
for %%a in (.) do set folder=%%~na
if "%folder%" == "workspaces" (
  set WORKSPACE=%last_folder%
)
cd..  
goto :iterate_backwards

:cli
if "%1%" == "" (
  goto :end
)
set "BASH=%ProgramFiles%\Git\bin\bash.exe"
if not exist "%BASH%" (
  set "BASH=%ProgramFiles(x86)%\Git\bin\bash.exe"
)
if exist "%BASH%" (
  "%BASH%" -c 'devon %*'
) else (
  echo:
  echo *** ATTENTION ***
  echo Bash has not been found on your system!
  echo Please install git for windows on your system.
)
goto :end

:printVersion
echo ${devon_ide_version}

:end
