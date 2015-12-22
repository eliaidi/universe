@echo off
echo [INFO] install module.
call mvn clean source:jar install -Dmaven.test.skip=true
pause