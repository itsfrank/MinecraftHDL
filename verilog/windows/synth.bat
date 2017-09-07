echo off
if "%1"=="" goto BLANK

copy %1 autoyosys\tmp.v
autoyosys\yosys -s autoyosys\auto.ysy
copy autoyosys\tmp.json %1.json
goto END

:BLANK
copy myfile.v autoyosys\tmp.v
autoyosys\yosys -s autoyosys\auto.ysy
copy autoyosys\tmp.json myfile.v.json

:END
PAUSE
