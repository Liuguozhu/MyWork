cd ..
call mvn clean install
pause
cd deploy
plink -i tlqy.ppk tlqy@101.251.106.242 "cd /home/tlqy/temp && rm -rf *"
pscp -r -i tlqy.ppk ..\target\NewTLOL.jar ..\target\json ..\target\lib ..\target\lpb ..\target\spring tlqy@101.251.106.242:/home/tlqy/temp
plink -i tlqy.ppk tlqy@101.251.106.242 "cd /home/tlqy/ol_2 && mkdir bak20141017 && mv json lib lpb spring bak20141017"
pause