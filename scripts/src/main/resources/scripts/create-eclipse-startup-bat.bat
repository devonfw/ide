@echo off

if not exist eclipse-%WORKSPACE%.bat (

echo @echo off>eclipse-%WORKSPACE%.bat
echo pushd %%~dp0>>eclipse-%WORKSPACE%.bat
echo set WORKSPACE=%WORKSPACE%>>eclipse-%WORKSPACE%.bat
echo call %SCRIPTS_PATH%\eclipse-start.bat>>eclipse-%WORKSPACE%.bat
echo popd>>eclipse-%WORKSPACE%.bat

echo Created eclipse-%WORKSPACE%.bat

)