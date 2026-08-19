# Minecraft 26.1 Fabric migration record

## Upstream relationship

- Fabric branch: `fabric/26.1`
- Upstream branch: `upstream/26.1`
- Audited upstream tip: `f0c32aecbaba76f010ee520a9ed987c15fa262a5`
- Upstream release tag: `26.1.5` (`e2fa606f297031a50dd569f53c94623a72168fd5`)
- Port version: `26.1.5`
- Minecraft range: 26.1–26.1.2

This branch was created directly from the upstream 26.1 tip. The validated 26.2
Fabric adapter commit was then applied as a platform-only delta and resolved
against 26.1's own source and dependencies. No 26.2 core source was used as the
baseline.

## Version-specific dependencies

- Minecraft 26.1
- Fabric Loader 0.19.3
- Fabric API 0.149.1+26.1.2 (its metadata covers the 26.1 release line)
- Fabric Loom 1.17.19
- Forge Config API Port 26.1.5
- Java 25 and Gradle 9.5.1

## Platform notes

The platform mappings and safe optional integrations match the 26.2 port, but
compile against the 26.1 Minecraft/Fabric API surface. Core backup, chain,
compression, merge, file, and storage logic remains the upstream 26.1 code.

The release JAR bundles XZ, zstd-jni, and commons-compress. As with 26.2, the
published artifact is used for compression runtime checks because the 26.x Loom
source-mode run does not expose nested `include()` libraries to the game
classloader.

## Validation performed

- `gradlew build`: success with the 26.1 toolchain and dependencies.
- Production Fabric dedicated server: reached `Done` using the built JAR.
- Manual ZIP, ZSTD, and SBK backup commands: completed with non-empty archives.
- Automatic no-player backup and an incremental child archive: completed.
- Merge/read path: merged the incremental chain and retained a post-full-backup
  test file.
- Client: started with the 26.1 Fabric environment and loaded SimpleBackups.

Generated test worlds and archives remain under ignored `run*` directories.
