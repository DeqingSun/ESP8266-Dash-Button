make
IF %ERRORLEVEL% NEQ 0 GOTO LOCOFBRANCH

  esptool.py -p COM6 write_flash 0x00000 ../bin/eagle.flash.bin 0x40000 ../bin/eagle.irom0text.bin

:LOCOFBRANCH
