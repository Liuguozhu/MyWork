cd ..
call mvn clean install
pause
start http://192.168.0.246:1234/invoke?operation=stopService^&objectname=NewTLOL%%3Aname%%3DNewTLOL+MANAGER^&value0=123456654321^&type0=java.lang.String
cd deploy
plink -i newtlol.ppk newtlol@192.168.0.246 "cd /app/newtlol/tlol && rm -r `ls -1 | grep -E '^json$|^lib$|^lpb$|^spring$|^NewTLOL.jar$'`"
pscp -r -i newtlol.ppk ..\target\NewTLOL.jar ..\target\json ..\target\lib ..\target\lpb ..\target\spring newtlol@192.168.0.246:/app/newtlol/tlol
plink -i newtlol.ppk newtlol@192.168.0.246 "cd /app/newtlol/tlol && ./restart.sh"
pause