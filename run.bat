@echo off
cd /d "%~dp0"
echo Compiling Main.java...
javac -cp safe-pipeline-1.0.0.jar src\main\java\com\example\app\Main.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Running Main...
java -cp "safe-pipeline-1.0.0.jar;src\main\java\com\example\app" Main
pause
