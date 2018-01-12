@echo off
setlocal ENABLEDELAYEDEXPANSION


rem %1		source path
rem %2		source list
rem %3		destination path


:main
	call :check_param %1 %2 %3

	for /f %%i in (%2) do (
		call :copy_source "%%i"
	)
goto :eof


:check_param
	call :remove_last_backslash %1
	set path_src=%ret01%
	call :clear_retxx
	
	if not exist %2 (
		@echo "file list is not exist"
		goto :eof
	)
	
	call :remove_last_backslash %3
	set path_dst=%ret01%
	call :clear_retxx
goto :eof


:copy_source
	if %1=="" goto :eof
	set file_name=%~1
	set file_name=!file_name:/=\!
	@echo f | xcopy /r/y/s %path_src%\!file_name!		%path_dst%\!file_name!
goto :eof


rem ##################	common	##################
:clear_retxx
	set ret01=
	set ret02=
	set ret03=
	set ret04=
	set ret05=
goto :eof


:remove_last_backslash
	set p=%~1
	set ret01=!p!
	set last=!p:~-1,1!
	if "%last%" == "\" set ret01=!p:~1!
goto :eof