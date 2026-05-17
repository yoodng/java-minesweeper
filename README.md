# MineClear 扫雷

学Java时练习写着小游戏。最近整理电脑文件翻出来了，push 上来留个纪念。

## 游戏操作

- **左键单击**：翻开格子
- **右键单击**：标记/取消标记雷旗
- 翻出所有非雷格子即获胜，踩到雷则游戏结束

## 三级难度

| 难度 | 棋盘 | 雷数 |
|------|------|------|
| 简单 | 9×9 | 10 |
| 中级 | 16×16 | 40 |
| 高级 | 16×30 | 99 |

## 快速运行

要求 Java 8+，下载 `MineClear.jar` 后：

```bash
java -jar MineClear.jar
```

## 从源码构建

```bash
# 编译
javac -encoding utf-8 -d build/classes -cp "lib/*" src/jav/*.java src/Dao/*.java src/util/*.java

# 打包（将所有依赖解压合并）
mkdir -p build/fat
cd build/fat
for jar in ../../lib/*.jar; do jar xf "$jar"; done
rm -rf META-INF/
cp -r ../classes/* ./
jar cfm ../MineClear.jar ../MANIFEST.MF .
```
##

- Java Swing 桌面应用
- SQLite（JDBC）数据持久化
- 依赖：TableLayout、MigLayout、JGoodies Forms、SwingX、IntelliJ Forms Runtime
- IntelliJ IDEA 项目

## 项目结构

```
src/
├── jav/          # 入口和 UI 层（Board.java 主窗口）
│   ├── Board.java       # 主窗口、事件处理、计时器
│   ├── Game.java        # 游戏逻辑控制
│   ├── MineArea.java    # 雷区数据（布雷、计算邻雷数）
│   ├── GameData.java    # 战绩数据模型
│   ├── ShowGameData.java # 历史战绩窗口
│   └── ImageType.java   # 图片资源枚举
├── Dao/          # 数据访问层
│   ├── DataOperation.java    # 接口
│   └── DataOprationImpl.java # SQLite 实现
└── util/         # 工具类
    └── SqliteJDBC.java  # SQLite 连接
```

## License

MIT
