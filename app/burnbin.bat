@echo off
set BACKPATH=%PATH%
set PATH=%BACKPATH%;%CD%\..\tools
set PATH=%PATH%;C:\Python27

rem call gen_misc.bat
python ..\tools\esptool.py  write_flash 0x000000 ..\bin\Fans_SDK_flash.bin

