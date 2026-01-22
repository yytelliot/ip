@ECHO OFF
SETLOCAL

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM collect all java files under src/main/java into SOURCES.TXT
dir /s /b ..\src\main\java\*.java > SOURCES.TXT

REM compile everything
javac -Xlint:none -d ..\bin -sourcepath ..\src\main\java @SOURCES.TXT

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    del SOURCES.TXT
    exit /b 1
)
REM no error here, errorlevel == 0

REM clean up temporary file
del SOURCES.TXT

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin pookie.Pookie < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
