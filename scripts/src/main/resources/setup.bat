@echo off
if NOT "%DEVON_IDE_TRACE%"=="" echo on

set _fBGreen=[92m
set _RESET=[0m

echo "%PSModulePath%" | findstr "%USERPROFILE%\Documents\WindowsPowerShell" >NUL
if "%ERRORLEVEL%" == "0" (
  echo Starting CMD window as workaround since setup has been called from powershell...
  set PSModulePath=
  start "CMD window" "%~f0"
  goto :EOF
)

pushd %~dp0
echo Setting up your devonfw-ide in %CD%
call scripts\devon.bat ide setup %*
if %ERRORLEVEL% neq 0 (
  echo Error occurred while running devon.bat ide setup.
  exit /b %ERRORLEVEL%
)
reg import system/windows/cmd/devon-cmd.reg >nul 2>&1
reg import system/windows/power-shell/devon-power-shell.reg >nul 2>&1

dir %USERPROFILE%\AppData\Local\Microsoft\WindowsApps\wt.exe >NUL 2>NUL
if "%ERRORLEVEL%" == "0" (
  reg import system/windows/windows-terminal/devon-wt-cmd.reg >nul 2>&1
  reg import system/windows/windows-terminal/devon-wt-gitbash.reg >nul 2>&1
  reg import system/windows/windows-terminal/devon-wt-ps.reg >nul 2>&1
)

for /F "usebackq tokens=2*" %%O in (call "%SystemRoot%"\system32\reg.exe query "HKLM\Software\Cygwin\setup" /v "rootdir" 2^>nul ^| "%SystemRoot%\system32\findstr.exe" REG_SZ) do set CYGWIN_HOME=%%P
if exist "%CYGWIN_HOME%\bin\bash.exe" (
  reg import system/windows/cygwin/devon-cygwin.reg >nul 2>&1
  "%CYGWIN_HOME%\bin\bash.exe" -l -c "cd "%CD%";./scripts/devon"
)

echo %_fBGreen%Setup of devonfw-ide completed%_RESET%
if not "%1%" == "-b" (
  pause
)

popd	
