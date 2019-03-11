Summary:
This is a central solution for the OASP4J IDE that should be "installed" only once on a windows system.
It allows to automatically set the environment variables properly no matter where you are in your directory.

Installtion:
1. Copy "IDEenv.bat" into a central script directory of your choice.

2. Ensure that this directoy is added to your system path:
Computer > Properties > Advanced system settings > Environment Variables > User variables for ... > PATH
Add ; followed by the absolute path of that directory
Open Command propmpt and type "IDEenv" hit [return] and ensure you do NOT get:
'IDEenv' is not recognized as an internal or external command, operable program or batch file.

3. Right-click on the file "IDEenv.reg" and choose "Merge" from Context-Menu.
Confirm the dialog "User Account Control" with "Yes".
Confrim the dialog "Registry Editor" with "Yes".
Click "OK".

Result:
Now whenever you right-click on a folder in Windows-Explorer you get the option "Open CMD here" from the context menu.
If you select it, a commandline opens. In case the directory is located inside a OASP4J IDE, then all environment variables
have automatically been set for you and you will see the message "IDE environment has been initialized.".
Then you can call maven (mvn) or other commands and everything will use the software specific for your OASP4J IDE project.
Otherwise no message appears and the environment variables remain untouched.