@echo off

pushd %~dp0

rem sets the required environment variables like JAVA_HOME, M2_REPO...
call scripts\environment-project.bat

popd

if "%CYGWIN_HOME%" == "" (
  if exist "C:\cygwin\bin\mintty.exe" (
    set CYGWIN_HOME="C:\cygwin"
  )
  if exist "C:\cygwin64\bin\mintty.exe" (
    set CYGWIN_HOME="C:\cygwin64"
  )
  if exist "C:\Program Files (x86)\bin\mintty.exe" (
    set CYGWIN_HOME="C:\Program Files (x86)"
  )
  if exist "C:\Program Files\bin\mintty.exe" (
    set CYGWIN_HOME="C:\Program Files"
  )
  if "%CYGWIN_HOME%" == "" (
    echo "**********************************************"
    echo "************* CYGWIN WAS NOT FOUND ***********"
    echo "* Please install Cygwin to standard location *"
    echo "* or set CYGWIN_HOME to your custom location *"
    echo "**********************************************"
    pause
    exit -1
  )
)
start %CYGWIN_HOME%\bin\mintty.exe -i /Cygwin-Terminal.ico /bin/env CHERE_INVOKING=1 TERMINAL_TITLE=[PNR] /bin/bash -l