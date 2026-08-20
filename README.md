# Simple Backups Fabric

<p align="center">
  <img src="src/main/resources/assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled
and on-demand Minecraft world backup mod created by the upstream authors.

This branch targets **Minecraft 1.21.11** and corresponds to upstream
**SimpleBackups 21.11.6**. Each Minecraft line is ported independently from its
matching upstream source instead of sharing cross-version compatibility code.

## Current 1.21.11 support

- Scheduled and manual full, incremental, and differential backups
- ZIP archives and backup-chain merging
- Existing TOML configuration names, keys, defaults, and directory layout
- Pause-state networking and client HUD
- Dedicated-server and integrated-server operation
- Optional Cherished Worlds and mc2discord integration, isolated when absent

Required runtime mods are Fabric Loader, Fabric API, and Forge Config API Port.
Mod Menu is optional and exposes the configuration screen.

## Build

Minecraft 1.21.11 requires Java 21. The Gradle toolchain can download a matching
JDK automatically:

```powershell
.\gradlew.bat build
```

The release JAR is written to `build/libs/`.

## Version maintenance

- [1.21.11 migration record](docs/MIGRATION_1.21.11.md)
- [Fabric porting playbook](docs/PORTING_PLAYBOOK.md)
- [Version matrix and branch policy](docs/VERSION_MATRIX.md)
- [Future upstream synchronization workflow](docs/UPSTREAM_SYNC.md)

## Attribution and license

This is not an official Fabric release by the upstream SimpleBackups project.
Original authorship and attribution are retained in [NOTICE](NOTICE). The
project remains licensed under the [Apache License 2.0](LICENSE).

Official upstream releases remain available on
[Modrinth](https://modrinth.com/mod/simple-backups) and
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/simple-backups).
