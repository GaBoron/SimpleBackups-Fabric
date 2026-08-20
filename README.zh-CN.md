# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · **简体中文** · [繁體中文](README.zh-TW.md)

<img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">

这是 [SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) 的非官方、
由社区维护的 Fabric 移植版。项目在保留对应上游版本的定时和手动世界备份行为
的同时，将 Forge 与 NeoForge 平台实现替换为 Fabric 实现。

> [!IMPORTANT]
> 本项目不是 SimpleBackups 原作者发布的官方 Fabric 版本。Fabric 移植版的
> 问题请提交到本仓库，不要向上游项目反馈。

## 下载

请选择与你的 Minecraft 版本完全匹配的 JAR。

| Minecraft / 移植版本 | 必需的 Fabric 前置 Mod | Java | 下载 / 源码 |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | Java 25+ | [下载 JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar)<br>[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | Java 25+ | [下载 JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar)<br>[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | Java 21+ | [下载 JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar)<br>[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) |

每条源码分支都以对应的 SimpleBackups 上游分支为基础，并且可以独立构建。
此默认分支只保存已发布的构建产物和项目级说明。

## 安装

1. 安装与你的 Minecraft 版本对应的 Fabric Loader。
2. 安装表格中对应版本的 Fabric API 和 Forge Config API Port。
3. 将 SimpleBackups Fabric JAR 及其前置 Mod 放入 `mods` 文件夹。
4. 启动一次游戏或专用服务端，以生成配置文件。

每个 JAR 的 `fabric.mod.json` 中都声明了所需依赖版本。如果必需的 Mod
缺失或版本不兼容，Fabric Loader 会显示明确的依赖错误。

## 功能

- 世界运行时按计划自动备份
- 使用 `/simplebackups backup start` 命令手动备份
- 完整、增量和差异备份模式
- 保留 SimpleBackups 原有配置名称和目录结构
- 备份链合并和存储空间限制
- 支持专用服务端和集成服务端
- 未安装相应 Mod 时安全隔离可选兼容功能

压缩格式和高级选项以对应的上游版本为准。例如，1.21.11 版本线保留其当时
仅使用 ZIP 的行为；较新的版本线则包含对应上游源码支持的压缩格式。

## 版权与许可证

SimpleBackups 由上游项目及其贡献者创建。本 Fabric 移植版保留上游的
Apache License 2.0 和 attribution，详情请参阅 [LICENSE](LICENSE) 与
[NOTICE](NOTICE)。
