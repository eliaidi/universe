@echo off

echo [INFO] deploy module.

cd %~dp0
cd ..
call mvn clean deploy
cd bin

pause