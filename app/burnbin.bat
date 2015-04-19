@echo off
set BACKPATH=%PATH%
set PATH=%BACKPATH%;%CD%\..\tools
set PATH=%PATH%;C:\Python27

IF [%1] == [] GOTO LOCOFBRANCH
set COMPORT=-p %1
GOTO ENDOFBRANCH
:LOCOFBRANCH
set COMPORT= 
:ENDOFBRANCH


rem call gen_misc.bat
python ..\tools\esptool.py %COMPORT% write_flash 0x000000 ..\bin\Fans_SDK_flash.bin

