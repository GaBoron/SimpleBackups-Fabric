# Simple Backups Fabric

**English** · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). It preserves
the scheduled and on-demand world-backup behavior of the corresponding upstream
version while replacing Forge and NeoForge platform integration with Fabric.

> [!IMPORTANT]
> This project is not an official Fabric release by the SimpleBackups authors.
> Please report Fabric-port issues in this repository rather than to upstream.

## Downloads

Choose the JAR that exactly matches your Minecraft version.

| Minecraft / Port | Required Fabric mods | Java | Download / Source |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | Java 25+ | [Download JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar)<br>[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | Java 25+ | [Download JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar)<br>[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | Java 21+ | [Download JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar)<br>[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) |

Each source branch starts from the matching upstream SimpleBackups branch and
remains independently buildable. This default branch contains published build
artifacts and project-level information only.

## Installation

1. Install the Fabric Loader for the matching Minecraft version.
2. Install the matching versions of Fabric API and Forge Config API Port.
3. Place the SimpleBackups Fabric JAR and its dependencies in the `mods` folder.
4. Start the game or dedicated server once to generate the configuration files.

The required dependency versions are declared in each JAR's `fabric.mod.json`.
Fabric Loader will report a clear dependency error if a required mod is missing
or incompatible.

## Features

- Scheduled backups when a world is running
- Manual backups through the `/simplebackups backup start` command
- Full, incremental, and differential backup modes
- Existing SimpleBackups configuration names and directory layout
- Backup-chain merging and storage-limit handling
- Dedicated-server and integrated-server support
- Optional compatibility integrations isolated when their mods are absent

Archive formats and advanced options follow the corresponding upstream version.
For example, the 1.21.11 line retains its period-correct ZIP-only behavior,
while newer version lines include the compression formats supported by their
matching upstream source.

## Attribution and license

SimpleBackups was created by the upstream project and contributors. This Fabric
port retains the upstream Apache License 2.0 and attribution; see [LICENSE](LICENSE)
and [NOTICE](NOTICE). Maintainer requirements for derivative source files and
Modrinth releases are recorded in [the publication policy](docs/PUBLISHING.md).
