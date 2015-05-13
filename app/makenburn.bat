make
IF %ERRORLEVEL% NEQ 0 GOTO LOCOFBRANCH


  IF [%1] == [] GOTO LOCOFCOMBRANCH
  set COMPORT=-p %1
  GOTO ENDOFCOMBRANCH
  :LOCOFCOMBRANCH
  set COMPORT= 
  :ENDOFCOMBRANCH

  esptool.py %COMPORT% write_flash 0x00000 ../bin/eagle.flash.bin 0x40000 ../bin/eagle.irom0text.bin

:LOCOFBRANCH
