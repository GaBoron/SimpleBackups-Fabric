# Minecraft 26.2 Fabric migration record

## Upstream relationship

- Fabric branch: `fabric/26.2`
- Upstream branch: `upstream/26.2`
- Audited upstream tip: `f81e4969f77b665833e28f96835f05d812a3828b`
- Upstream release tag: `26.2.1` (`127f376`)
- Port version: `26.2.1`

The branch was created directly from the upstream 26.2 tip. Compare the complete
platform port with:

```text
git diff upstream/26.2...fabric/26.2
```

## Preserved upstream code

The backup chain manager, incremental/differential selection, archive writers and
readers, merge implementations, file visitors, disk-space checks, backup naming,
metadata, configuration keys/defaults, and output layout remain upstream code.
Only narrow Minecraft/platform seams were changed.

## Platform mappings

| Upstream NeoForge facility | Fabric implementation |
| --- | --- |
| `@Mod` constructor | `ModInitializer` entrypoint in `fabric.mod.json` |
| Dist/client registration | separate `src/client` source set and `ClientModInitializer` |
| mod/event bus listeners | Fabric command, server tick, join, and disconnect callbacks |
| command event | `CommandRegistrationCallback` |
| custom payload registration/send | Fabric payload registry and play networking APIs |
| client HUD event | Fabric HUD element registry |
| FML game/config paths | `FabricLoader` game and config directories |
| access transformer | official-namespace Fabric access widener |
| `FMLTranslations` | bundled `en_us` server translation fallback adapter |
| NeoForge config registration/screen | Forge Config API Port on Fabric |

Forge Config API Port deliberately exposes compatible `net.neoforged` config
types. Those remaining imports are provided by a Fabric mod and preserve the
existing TOML schema; they are not a NeoForge runtime dependency or event layer.

## Non-identical equivalents

- Cherished Worlds stores favorites in a client-side list. The optional adapter
  runs only on a physical client and uses the Fabric release when installed. A
  dedicated server safely treats the unavailable client list as unfiltered.
- mc2discord is optional and invoked only when its Fabric mod is present. Failure
  to resolve its API is logged and never prevents SimpleBackups from loading.
- Fabric has no direct server-side `FMLTranslations` helper, so server log and
  command feedback format the bundled English strings while player messages
  remain normal translatable components.
- Fabric Loom 1.17 has an open Minecraft 26.2 development-run limitation where
  nested `include()` libraries are not added to source-mode game runs. The built
  release JAR does contain and declare the nested libraries, so archive formats
  were validated again with that production artifact in a standalone Fabric
  Server rather than trusting `runServer` alone.

## Validation performed

- `gradlew build`: success on Gradle 9.5.1 and Java 25.
- Fabric development dedicated server: reached `Done` without client classes.
- Production JAR dedicated server: reached `Done` and loaded nested XZ,
  zstd-jni, and commons-compress libraries.
- Manual ZIP, ZSTD, and SBK backups: created valid non-empty archives.
- Automatic no-player backup: triggered from TOML configuration.
- Incremental chain: a post-full-backup file appeared in `child-0001.zip` and
  the chain metadata recorded it as a child.
- ZIP incremental, ZSTD, and SBK merge paths: completed; merged ZIP retained the
  changed file.
- Client: entered an integrated world; backup start/finish messages rendered and
  the network/HUD path did not crash.

Test worlds and generated archives live only under ignored `run*` directories.
