# Simple Backups Fabric

<p align="center">
  <img src="src/main/resources/assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of [SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled and on-demand Minecraft world backup mod created by the upstream authors.

This branch targets **Minecraft 1.20.1** and follows the upstream **1.20.x / 3.1** source line. It is ported directly from that version's source.

> [!IMPORTANT]
> This project is not an official Fabric release by the SimpleBackups authors. Please report Fabric-port issues in this repository.

## Features

- Scheduled and manual world backups through `/simplebackups backup start`
- Full and modified-since-last/full ZIP backups, merging, filtering, retention, and storage limits
- Original SimpleBackups TOML configuration keys, defaults, and backup layout
- Pause-state client HUD and optional Cherished Worlds and mc2discord integrations

## Requirements

| Component | Required version |
| --- | --- |
| Minecraft | 1.20.1 |
| Fabric Loader | 0.19.3 or newer |
| Fabric API | 0.92.12+1.20.1 or newer compatible 1.20.1 build |
| Forge Config API Port | 8.0.0 or newer compatible 1.20.1 build |
| Java | 17 or newer |

Install Fabric API and Forge Config API Port alongside this mod. Mod Menu is optional.

## Build

With Java 17 or newer available:

```powershell
.\gradlew.bat build
```

## Credits and license

SimpleBackups is created by the [upstream authors](https://github.com/ChaoticTrials/SimpleBackups). This community port retains the Apache-2.0 license; see [LICENSE](LICENSE) and [NOTICE](NOTICE).
