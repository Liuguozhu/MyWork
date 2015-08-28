cd ..
call mvn clean install
pause
cd deploy
plink -i newtlol.ppk newtlol@192.168.0.246 "cd /app/newtlol/tlol && rm -r `ls -1 | grep -E '^NewTLOL.jar$'`"
pscp -r -i newtlol.ppk ..\target\NewTLOL.jar newtlol@192.168.0.246:/app/newtlol/tlol
plink -i newtlol.ppk newtlol@192.168.0.246 "cd /app/newtlol/tlol && ./restart.sh"
pause