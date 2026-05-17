# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

- **Run**: Run `jav.Board` as the main class (entry point, contains `main` method)
- **Build artifact**: IntelliJ artifact config at `.idea/artifacts/MineClear_jar.xml` — jar with dependencies
- **Dependencies**: All JARs in `lib/` directory, no Maven/Gradle. IntelliJ library configs at `.idea/libraries/*.xml`
- **Database**: SQLite via JDBC (`org.sqlite.JDBC`), connection utility at `src/util/SqliteJDBC.java`. Data file: `GameData.db`

## Architecture

Swing桌面扫雷游戏，按MVC组织：

- **Entry/UI层** (`src/jav/`): `Board.java` 是主窗口和游戏控制器，负责UI构建(JFormDesigner)、鼠标事件处理、计时器、游戏状态控制。`ShowGameData.java` 显示历史战绩。`ImageType.java` 枚举16种图片资源(数字1-8、炸弹、表情等)。
- **游戏逻辑** (`src/jav/`): `Game.java` 持有 `MineArea`。`MineArea.java` 管理雷区数据——`boolean[][] mines` 记录雷位置，`int[][] nearMineCount` 缓存各格相邻雷数，构造函数自动布雷并计算。
- **数据持久化** (`src/Dao/`): `DataOperation.java` 接口定义 `insertData`/`getGameData`。`DataOprationImpl.java` 通过 SQLite JDBC 操作 `GameData.db` 的 `t_GameData` 表(level, time, date)。`GameData.java` 模型类兼有save/read静态方法。
- **工具** (`src/util/`): `SqliteJDBC.java` 提供 SQLite 连接单点。

### 三级难度
- 简单: 9×9, 10雷
- 中级: 16×16, 40雷
- 高级: 16×30, 99雷
