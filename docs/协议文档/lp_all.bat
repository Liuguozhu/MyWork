@del *.lpb

set a=file error : 

@for %%i in (*.mes) do (
@	lp.exe -o %%i
@	if exist %%i.lpb (
		del .\..\..\src\main\resources\lpb\%%i.lpb 
		copy %%i.lpb .\..\..\src\main\resources\lpb
	) else (
		echo ++++++++++++++++++++++
		set "a=%a% %%i"
	)
)

@echo ***************************************************
@echo %a%
pause




