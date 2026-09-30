<!-- Modified by the Simple Backups Fabric project in 2026. Adapted from upstream SimpleBackups; Apache-2.0. -->
# Simple Backups Fabric

<p align="center">
  <img src="src/main/resources/assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled
and on-demand Minecraft world backup mod created by the upstream authors.

This version targets **Minecraft 26.3** and corresponds to upstream
**SimpleBackups 26.3.0**.

## Features

- Scheduled and manual full, incremental, and differential backups
- ZIP, ZSTD, and SBK archives, including chain merging
- Existing TOML configuration names, keys, defaults, and directory layout
- Pause state networking and client HUD
- Dedicated server and integrated server operation
- Optional Cherished Worlds and mc2discord integration, isolated when absent

Required runtime mods are Fabric Loader **0.19.5 or newer**, Fabric API
**0.161.0+26.3 or newer**, and Forge Config API Port **26.3.1 or newer**.
Install the Minecraft 26.3 versions of these mods, then place the Simple Backups
Fabric JAR in the `mods` folder. Start a manual backup with
`/simplebackups backup start`.

Mod Menu is optional and exposes the configuration screen. XZ and zstd-jni are
bundled in the release JAR together with their license texts. Commons Compress
is also bundled.

[Downloads and documentation in all supported languages](https://github.com/GaBoron/SimpleBackups-Fabric#downloads)
are available on the main project page.

## Build

Minecraft 26.3 requires Java 25. The Gradle toolchain can download a matching
JDK automatically:

```powershell
.\gradlew.bat build
```

The release JAR is written to `build/libs/`.

## Attribution and license

This is not an official Fabric release by the upstream SimpleBackups project.
Original authorship and attribution are retained in [NOTICE](NOTICE). The
project remains licensed under the [Apache License 2.0](LICENSE). Bundled XZ and
zstd-jni license texts are retained in [licenses](licenses/).

Official upstream releases remain available on
[Modrinth](https://modrinth.com/mod/simple-backups) and
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/simple-backups).
