<!-- Modified by the Simple Backups Fabric project in 2026. Adapted from upstream SimpleBackups; Apache-2.0. -->
# Simple Backups Fabric

<p align="center">
  <img src="src/main/resources/assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), the scheduled
and on-demand Minecraft world backup mod created by the upstream authors.

GUI preview **26.3.0-gui** targets **Minecraft 26.3** and is based on upstream
**SimpleBackups 26.3.0**.

## Features

- Scheduled and manual full, incremental, and differential backups
- ZIP, ZSTD, and SBK archives, including chain merging
- Existing TOML configuration names, keys, defaults, and directory layout
- Pause state networking and client HUD
- Restore backups from the singleplayer world selection screen
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

Select a world in the singleplayer menu and click **Restore** beside the search
field. Choose a backup, then restore it as a new world or replace the selected
world. New worlds use `original-name-YYYY-MM-DD_HH-mm-ss`, based on the selected
backup's time; existing folder names are preserved by adding a numeric suffix.
Incremental backups include their preceding archives; differential
backups use the full backup and the selected snapshot.
The next backup of a restored world starts a new full backup chain.

Before replacement, the current world is compressed using the configured backup
format and filters. This **Rollback** snapshot appears in the same restore list
and follows the normal chain-count and storage limits. If creating it fails,
replacement is cancelled. Temporary installation files are cleared on success.

Backups are read from the configured output directory. Restoration is available
for local worlds while they are closed; multiplayer server backups are not
accessible from this screen.

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
