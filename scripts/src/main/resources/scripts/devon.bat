@echo off
setlocal ENABLEDELAYEDEXPANSION

if NOT "%DEVON_IDE_TRACE%"=="" echo on
if "%1%" == "-v" (
  goto :print_version
)
if "%1%" == "--version" (
  goto :print_version
)

Set _fBYellow=[93m
Set _fBGreen=[92m
Set _fBRed=[91m
Set _RESET=[0m

rem Auto-install ourselves...
if not exist "%USERPROFILE%\scripts" (
  md "%USERPROFILE%\scripts"
)
if not exist "%USERPROFILE%\scripts\devon.bat" (
  echo Copying devon CLI script to your home directory...
  copy "%~f0" "%USERPROFILE%\scripts\devon.bat"
)
rem https://stackoverflow.com/questions/61888625/what-is-f-in-the-for-loop-command

for %%f in (devon.bat) do set "p=%%~$PATH:f"
if not defined p (
  echo Adding %USERPROFILE%\scripts to your users system PATH
  for /F "tokens=2* delims= " %%f IN ('reg query HKCU\Environment /v PATH ^| findstr /i path') do set USER_PATH=%%g
  if "!USER_PATH:~-1,1!" == ";" (
    set "USER_PATH=!USER_PATH:~0,-1!"
  )
  if "!USER_PATH!" == "" (
    setx PATH "%%USERPROFILE%%\scripts"
    echo %_fBYellow%"ATTENTION:"%_RESET%
    echo "Your user PATH environment variable has not been previously set."
    echo "You may need to log-off and log-in again so your PATH changes are properly applied."
    echo "Otherwise you may get errors that devon command has not been found."
    pause
  ) else (
    setx PATH "!USER_PATH!;%%USERPROFILE%%\scripts"
  )
  if "!PATH:~-1,1!" == ";" (
    set "PATH=!PATH!%USERPROFILE%\scripts"
  ) else (
    set "PATH=!PATH!;%USERPROFILE%\scripts"
  )
  echo %_fBGreen%The devon CLI script has been installed to your windows system.%_RESET%
  echo %_fBGreen%Now in any new command shell, you can call devon to setup your IDE enviromennt variables.%_RESET%
  echo %_fBGreen%You can also provide arguments to devon for advanced usage, e.g. try calling 'devon help'%_RESET%
)

if "%1%" == "" (
  goto :setup_env
)

:git_installation
rem Search GitForWindows Installation - prefer user over machine result
for %%H in ( HKEY_LOCAL_MACHINE HKEY_CURRENT_USER ) do for /F "usebackq tokens=2*" %%O in (`call "%SystemRoot%"\system32\reg.exe query "%%H\Software\GitForWindows" /v "InstallPath" 2^>nul ^| "%SystemRoot%\system32\findstr.exe" REG_SZ`) do set GIT_HOME=%%P

if exist "%GIT_HOME%\bin\bash.exe" (
  set "BASH=%GIT_HOME%\bin\bash.exe"
  set "HOME=%USERPROFILE%"
  goto :bash_detected
)

echo %_fBYellow%WARNING: Git-bash is required but was not found at GIT_HOME=%GIT_HOME%.%_RESET%

rem If bash in GitForWindows could not be found search Cygwin Installation - prefer user over machine result
for %%H in ( HKEY_LOCAL_MACHINE HKEY_CURRENT_USER ) do for /F "usebackq tokens=2*" %%O in (`call "%SystemRoot%"\system32\reg.exe query "%%H\Software\Cygwin\setup" /v "rootdir" 2^>nul ^| "%SystemRoot%\system32\findstr.exe" REG_SZ`) do set CYGWIN_HOME=%%P

if exist "%CYGWIN_HOME%\bin\bash.exe" (
  set "BASH=%CYGWIN_HOME%\bin\bash.exe"
  set "HOME=%CYGWIN_HOME%\home\%USERNAME%"
  goto :bash_detected
)

rem If bash can not be autodetected allow the user to configure bash via BASH_HOME environment variable as fallback

if exist "%BASH_HOME%\bin\bash.exe" (
  set "BASH=%BASH_HOME%\bin\bash.exe"
  set "HOME=%USERPROFILE%"
  goto :bash_detected
)
echo:
echo %_fBYellow%*** ATTENTION ***%_RESET%
echo %_fBRed%ERROR: Could not find bash. It seems git for windows is not installed on your machine%_RESET%
echo %_fBRed%Please download and install git for windows from the following URL and after that rerun devonfw-ide setup:%_RESET%
echo %_fBRed%https://git-scm.com/download/win%_RESET%
pause
goto :eof

:bash_detected
if not "%DEVON_OLD_PATH%" == "" (
  set "DEVON_PATH=%PATH%"
  set "PATH=%DEVON_OLD_PATH%"
  set "DEVON_OLD_PATH="
)
if not exist "%HOME%\.devon\devon" (
  pushd %~dp0
  "%BASH%" -c 'source ./devon'
  popd
) else (
  if "%1" == "ide" (
    if "%2" == "setup" (
      pushd %~dp0
      "%BASH%" -c 'source ./devon'
      popd
    )
  )
)
if "%1%" == "bash" (
  "%BASH%"
) else (
  "%BASH%" -c 'source ~/.devon/devon %*'
  if not %ERRORLEVEL% == 0 (
    exit /b %ERRORLEVEL%
  )
)
if not "%DEVON_PATH%" == "" (
  set "DEVON_OLD_PATH=%PATH%"
  set "PATH=%DEVON_PATH%"
  set "DEVON_PATH="
)
pause
goto :eof

:setup_env
set "WORKSPACE=main"
pushd %CD%
:iterate_backwards
if exist scripts\environment-project.bat (
  set DEVON_IDE_HOME=%CD%
  call scripts\environment-project.bat
  echo devonfw-ide environment variables have been set for %CD% in workspace %WORKSPACE%
  popd
  goto :git_installation
)
if "%CD%" == "%CD:~0,3%" (
  popd
  echo You are not inside a devon IDE installation: %CD%
  pause
  goto :eof
)
set last_folder=%folder%
for %%a in (.) do set folder=%%~na
if "%folder%" == "workspaces" (
  if not "%last_folder%" == "workspaces" (
    set WORKSPACE=%last_folder%
  )
)
cd ..  
goto :iterate_backwards
pause
goto :eof

rem subroutine to print version
:print_version
echo $[devon_ide_version]
