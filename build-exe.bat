@echo off
echo === MineClear Build Script ===

echo [1/3] Compiling...
javac -encoding utf-8 -d build\classes -cp "lib/*" src\jav\*.java src\Dao\*.java src\util\*.java
if %errorlevel% neq 0 (echo Compile FAILED & pause & exit /b 1)

echo [2/3] Packing fat JAR...
if exist build\fat rmdir /s /q build\fat
mkdir build\fat
cd build\fat
for %%j in (..\..\lib\*.jar) do jar xf "%%j" 2>nul
if exist META-INF rmdir /s /q META-INF
xcopy ..\classes\* . /s /e /q >nul
jar cfm ..\MineClear.jar ..\MANIFEST.MF . 2>nul
cd ..\..
echo build\MineClear.jar created

echo [3/3] Running launch4j...
if exist "C:\Program Files (x86)\launch4j\launch4jc.exe" (
    set L4J="C:\Program Files (x86)\launch4j\launch4jc.exe"
) else if exist "C:\Program Files\launch4j\launch4jc.exe" (
    set L4J="C:\Program Files\launch4j\launch4jc.exe"
) else (
    echo launch4j not found, download from: https://sourceforge.net/projects/launch4j/
    pause & exit /b 1
)

%L4J% launch4j.xml
if exist MineClear.exe (
    echo SUCCESS: MineClear.exe created
) else (
    echo FAILED: open launch4j GUI with launch4j.xml manually
)

pause
