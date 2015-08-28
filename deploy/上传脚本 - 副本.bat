cd ..
call mvn clean install
pause
cd deploy
plink -i tlqy.ppk newtlol@192.168.0.246 "cd /home/tlqy/ol_2/temp && rm -rf *"
pscp -r -i tlqy.ppk ..\target\json  newtlol@192.168.0.246:/app/newtlol/tlol
start http://192.168.0.246:1234/invoke?operation=reloadJson^&objectname=NewTLOL%%3Aname%%3DNewTLOL+MANAGER
pause