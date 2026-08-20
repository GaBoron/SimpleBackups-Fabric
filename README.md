# Simple Backups Fabric

![Simple Backups Fabric icon](src/main/resources/assets/simplebackups/icon.png)

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled
and on-demand Minecraft world backup mod created by the upstream authors.

> **A complete, community-maintained Fabric port of SimpleBackups across
> multiple Minecraft versions, including historical releases and future
> upstream updates.**

The current completed port targets **Minecraft 26.2** and corresponds to
upstream **SimpleBackups 26.2.1**. Historical version lines are tracked and
ported independently from their matching upstream source; they are not emulated
from one large cross-version branch.

## Current 26.2 support

- Scheduled and manual full, incremental, and differential backups
- ZIP, ZSTD, and SBK archives, including chain merging
- Existing TOML configuration names, keys, defaults, and directory layout
- Pause state networking and client HUD
- Dedicated server and integrated server operation
- Optional Cherished Worlds and mc2discord integration, isolated when absent

Required runtime mods are Fabric Loader, Fabric API, and Forge Config API Port.
Mod Menu is optional and exposes the configuration screen. XZ and zstd-jni are
bundled in the release JAR together with their license texts.

## Build

Minecraft 26.2 requires Java 25. The Gradle toolchain can download a matching
JDK automatically:

```powershell
.\gradlew.bat build
```

The release JAR is written to `build/libs/`.

## Version lines and upstream sync

- [Version matrix and branch policy](docs/VERSION_MATRIX.md)
- [26.2 platform migration record](docs/MIGRATION_26.2.md)
- [Future upstream synchronization workflow](docs/UPSTREAM_SYNC.md)

The Fabric branches use the `fabric/<upstream-branch>` convention. Each line
records its upstream branch/tag/commit and remains independently buildable.

## Attribution and license

This is not an official Fabric release by the upstream SimpleBackups project.
Original authorship and attribution are retained in [NOTICE](NOTICE). The
project remains licensed under the [Apache License 2.0](LICENSE). Bundled XZ and
zstd-jni license texts are retained in [licenses](licenses/).

Official upstream releases remain available on
[Modrinth](https://modrinth.com/mod/simple-backups) and
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/simple-backups).
