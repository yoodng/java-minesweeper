@echo off
chcp 65001 >nul
echo === MineClear 扫雷 — 生成 exe ===

:: 1. 先构建 JAR
echo [1/3] 编译 Java 源码...
javac -encoding utf-8 -d build\classes -cp "lib/*" src\jav\*.java src\Dao\*.java src\util\*.java
if %errorlevel% neq 0 (echo 编译失败 & pause & exit /b 1)

echo [2/3] 打包 fat JAR...
if exist build\fat rmdir /s /q build\fat
mkdir build\fat
cd build\fat
for %%j in (..\..\lib\*.jar) do jar xf "%%j" 2>nul
if exist META-INF rmdir /s /q META-INF
xcopy ..\classes\* . /s /e /q >nul
jar cfm ..\MineClear.jar ..\MANIFEST.MF . 2>nul
cd ..\..
echo build\MineClear.jar 生成完成

:: 2. 生成 exe（需要 launch4j）
echo [3/3] 使用 launch4j 生成 exe...
if exist "C:\Program Files (x86)\launch4j\launch4jc.exe" (
    set L4J="C:\Program Files (x86)\launch4j\launch4jc.exe"
) else if exist "C:\Program Files\launch4j\launch4jc.exe" (
    set L4J="C:\Program Files\launch4j\launch4jc.exe"
) else (
    echo 未找到 launch4j，请下载安装：https://sourceforge.net/projects/launch4j/
    pause & exit /b 1
)

%L4J% launch4j.xml
if exist MineClear.exe (
    echo ✓ MineClear.exe 生成成功
) else (
    echo ✗ 生成失败，请手动打开 launch4j GUI 加载 launch4j.xml
)

pause
