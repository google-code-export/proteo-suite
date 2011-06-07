@echo off

set HEAP_SIZE=3072

set JAVA_COMMAND=java

set JAVA_PARAMETERS=-Xms%HEAP_SIZE%m -Xmx%HEAP_SIZE%m
set CLASS_PATH=D:\Resources\proteo-suite\dist\ProteoSuite.jar
rem set MAIN_CLASS=proteosuite.ProteoSuiteView.java 

%JAVA_COMMAND% -version
%JAVA_COMMAND% %JAVA_PARAMETERS% -jar %CLASS_PATH% 
rem %MAIN_CLASS%  

IF ERRORLEVEL 1 pause


