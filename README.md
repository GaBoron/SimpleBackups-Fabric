# SimpleBackups for Fabric

An unofficial, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). It preserves
the scheduled and on-demand world-backup behavior of the corresponding upstream
version while replacing Forge and NeoForge platform integration with Fabric.

> [!IMPORTANT]
> This project is not an official Fabric release by the SimpleBackups authors.
> Please report Fabric-port issues in this repository rather than to upstream.

## Downloads

Choose the JAR that exactly matches your Minecraft version.

| Minecraft | SimpleBackups Fabric | Download | Source branch |
| --- | --- | --- | --- |
| 26.2 | 26.2.1 | [Download JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) | [`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2) |
| 26.1–26.1.2 | 26.1.5 | [Download JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) | `fabric/26.1` (not published yet) |
| 1.21.11 | 21.11.6 | [Download JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) | [`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) |

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

## Validation status

Every build listed above has passed the project's baseline checks:

- the project builds and remaps successfully;
- opening or starting a world can trigger an automatic backup; and
- `/simplebackups backup start` creates a manual backup.

Deeper tests are run when a platform adapter changes or a regression is reported.

## Development and version branches

Source code is maintained in separate Minecraft-version branches so updates can
be compared directly with upstream without a large cross-version compatibility
layer. See the README and migration record in the relevant source branch for
build instructions and version-specific implementation notes.

Future ports will follow the same model: start from the matching upstream source,
apply the smallest Fabric platform adapter, preserve backup behavior, and publish
the validated JAR here.

## Integrity checks

SHA-256 checksums for the current files:

```text
F7538C4A3556D69D4074D9E42D5F818AFAFDB74BA0DCC588EFDE4F720F611471  simplebackups-fabric-21.11.6.jar
303A053E92FF9F8A9467977EE4412199EF8879B8ED838AF09C362B987AC6C86A  simplebackups-fabric-26.1.5.jar
6CF9A335F7613C49996CEADBF035D4218885D9E691D9C60070B4314BB7C8C051  simplebackups-fabric-26.2.1.jar
```

## Attribution and license

SimpleBackups was created by the upstream project and contributors. This Fabric
port retains the upstream Apache License 2.0 and attribution; see [LICENSE](LICENSE)
and [NOTICE](NOTICE).
