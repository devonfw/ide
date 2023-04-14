@echo off

set _fBGreen=[92m
set _fBRed=[91m
set _RESET=[0m

set "BEFORE_SETUP_PATH=%PATH%"
set "AFTER_SETUP_PATH=%BEFORE_SETUP_PATH%;%USERPROFILE%\scripts"

call setup.bat < NUL

rem Testcase: Is PATH added after running setup.bat
if "%PATH%" == "%AFTER_SETUP_PATH%" (
    echo %_fBGreen%Successfully added "%USERPROFILE%"\scripts to user path.%_RESET%
) else (
    echo %_fBRed%Failed to add "%USERPROFILE%"\scripts to user path.%_RESET%
    exit 1
)

rem Testcase: Are PATH, tools and devon.properties updated after running devon.bat
set "PATH=%SystemRoot%\System32;"
echo export TEST_VAR="test" >> scripts\devon.properties
call scripts/devon.bat
call mvn -v
if "%ERRORLEVEL%" == "0" (
        echo %_fBGreen%Successfully added PATH after running devon.bat.%_RESET%
) else (    
    echo %_fBRed%Failed to add PATH after running devon.bat.%_RESET%
    exit 1
)
if "%TEST_VAR%" == "test" (
    echo %_fBGreen%Successfully updated devon.properties after running devon.bat.%_RESET%
) else (    
    echo %_fBRed%Failed to update devon.properties after running devon.bat.%_RESET%
    exit 1
)






