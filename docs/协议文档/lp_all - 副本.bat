@echo on
setlocal  enabledelayedexpansion
del *.lpb
for %%i in (*.mes) do (
	set "old_name=%%i"
	set "new_name=!old_name:mes=mes.lpb!"
	lp.exe -o %%i
)
pause