# Simple Backups Fabric

<p align="center">
  <img src="src/main/resources/assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled
and on-demand Minecraft world backup mod created by the upstream authors.

This branch targets **Minecraft 1.21.1** and corresponds to upstream
**SimpleBackups 1.21-4.0.30**. It is ported directly from the matching upstream
source instead of being backported from a newer Minecraft version.

> [!IMPORTANT]
> This project is not an official Fabric release by the SimpleBackups authors.
> Please report Fabric-port issues in this repository rather than to upstream.

## Features

- Scheduled backups while a world is running
- Manual backups through `/simplebackups backup start`
- Period-correct full and modified-since-last ZIP backups
- Optional experimental full, incremental, and differential backup chains
- Backup-chain merging, filtering, retention, and storage-limit handling
- Existing SimpleBackups TOML configuration keys, defaults, and directory layout
- Pause-state networking and client HUD
- Dedicated-server and integrated-server support
- Optional Cherished Worlds and mc2discord integrations isolated when absent

## Requirements

| Component | Required version |
| --- | --- |
| Minecraft | 1.21.1 |
| Fabric Loader | 0.19.3 or newer |
| Fabric API | 0.116.15+1.21.1 or newer compatible 1.21.1 build |
| Forge Config API Port | 21.1.1 or newer compatible 1.21.1 build |
| Java | 21 or newer |

Mod Menu is optional and can expose the configuration screen supplied through
Forge Config API Port.

## Build

Run the branch with Java 21 available:

```powershell
.\gradlew.bat build
```

The release JAR is written to `build/libs/`.

## Attribution and license

This port retains the upstream Apache License 2.0 and attribution. See
[LICENSE](LICENSE) and [NOTICE](NOTICE). Official upstream releases remain
available on [Modrinth](https://modrinth.com/mod/simple-backups) and
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/simple-backups).
