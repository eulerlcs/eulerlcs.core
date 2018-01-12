@echo off
setlocal ENABLEDELAYEDEXPANSION


:prepare
	call :ini_timestamp
	set exe=%cd%\tool.180112.FilesCopier.bat
:main
	call :backup_list01

pause
goto :eof


:backup_list01
	set path_src=E:\21.cd.eulerlcs.core\2.code\jmr-61-collection\src\main
	set path_dst=.\jmr-61-collection\%yymmdd%.%hhmmss%
	mkdir %path_dst%
	
	call :make_list01 %path_src% %path_dst%.txt
	call %exe%	%path_src% %path_dst%.txt %path_dst% 
goto :eof


:make_list01
	set cmd_list="dir /b/s/a-d %1"
	
	for /f "usebackq delims==" %%i in (`%cmd_list%`) do (
		set f=%%i
		set f=!f:~57!
		if not "!f:~0,4!" == "obj\" (
		if not "!f:~0,4!" == "bin\" (
			@echo !f!>>%2
		)
		)
	)
goto :eof


rem ##################	common	##################
:ini_timestamp
	set yyyymmdd=%date:~0,4%%date:~5,2%%date:~8,2%
	set yymmdd=!yyyymmdd:~2,6!
	set hhmmss=%time:~0,2%%time:~3,2%%time:~6,2%
	if "!hhmmss:~0,1!" == " " set hhmmss=0!hhmmss:~1,7!
goto :eof
