# Simple Backups Fabric

**English** · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). It preserves
the scheduled and on-demand world-backup behavior of the corresponding upstream
version while replacing Forge and NeoForge platform integration with Fabric.

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> This project is not an official Fabric release by the SimpleBackups authors.
> Please report Fabric-port issues in this repository rather than to upstream.

## Downloads

Choose the release that matches your Minecraft version. The table links to
GitHub JARs; use the Modrinth badge above to browse versions published there.

### Standard Fabric ports

| Minecraft / port | Required Fabric mods | Java | Download |
| --- | --- | --- | --- |
| **26.3**<br>SimpleBackups Fabric 26.3.0 | Fabric Loader ≥ 0.19.5<br>Fabric API ≥ 0.161.0+26.3<br>Forge Config API Port ≥ 26.3.1 | 25+ | [GitHub JAR](artifacts/26.3/simplebackups-fabric-26.3.0.jar) |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [GitHub JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [GitHub JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [GitHub JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [GitHub JAR](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [GitHub JAR](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

Source branches: [`fabric/26.3`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3),
[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x),
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1), and
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x).

### GUI preview

Adds a **Restore** button to the singleplayer world selection screen. It can
restore existing full, incremental and differential backups from the standard
port in ZIP, ZSTD and SBK formats. Restore as a new world named after the original
and the backup time, or replace the selected world after backing it up. This
**Rollback** snapshot uses the configured format, appears in the same restore
list, and follows the normal backup retention and storage limits.

| Minecraft | GUI version / source | Download |
| --- | --- | --- |
| **26.3** | [26.3.0-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3-gui) | [GitHub JAR](artifacts/26.3-gui/simplebackups-fabric-26.3.0-gui.jar) |
| **26.1.x (26.1–26.1.2)** | [26.1.5-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1-gui) | [GitHub JAR](artifacts/26.1-gui/simplebackups-fabric-26.1.5-gui.jar) |

Each GUI version requires the same Fabric mods and Java version as its
corresponding port above. Install
this JAR in place of the standard port; keep only one SimpleBackups Fabric JAR
in `mods`.

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
and [NOTICE](NOTICE).
