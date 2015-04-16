make
IF %ERRORLEVEL% NEQ 0 GOTO LOCOFBRANCH
  call makebin
  burnbin.bat %1
:LOCOFBRANCH