md %USERPROFILE%/scripts
cp ../IDEenv.bat %USERPROFILE%/scripts
setx PATH "%PATH%;%USERPROFILE%/scripts"
regedit.exe /S IDEenv.reg
