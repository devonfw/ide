@echo off

if not exist vscode-%WORKSPACE_VS%.bat (

echo @echo off>vscode-%WORKSPACE_VS%.bat
echo pushd %%~dp0>>vscode-%WORKSPACE_VS%.bat
echo set WORKSPACE=%WORKSPACE_VS%>>vscode-%WORKSPACE_VS%.bat
echo call %SCRIPTS_PATH%\vscode-start.bat>>vscode-%WORKSPACE_VS%.bat
echo popd>>vscode-%WORKSPACE_VS%.bat

echo Created vscode-%WORKSPACE_VS%.bat

)