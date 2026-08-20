# Minecraft 1.21.11 Fabric migration record

## Upstream relationship

- Fabric branch: `fabric/1.21.x`
- Upstream branch: `upstream/1.21.x`
- Audited upstream tip: `17430e298775a0abc016fdf989a09bf81fe3455f`
- Published upstream version: `21.11.6`
- Port version: `21.11.6`
- Minecraft version: `1.21.11`

This branch was created directly from the upstream 1.21.x tip. Fabric platform
patterns proven on later ports were reapplied to this version's own source; no
26.x backup implementation was used as its baseline.

## Version-specific dependencies

- Minecraft 1.21.11
- Fabric Loader 0.19.3
- Fabric API 0.141.6+1.21.11
- Fabric Loom 1.17.19
- Forge Config API Port 21.11.1
- Java 21 and Gradle 9.5.1

## Preserved upstream behavior

This release predates upstream XZ, ZSTD, SBK, pre-copy, and multi-format merge
support. It intentionally retains the period-correct ZIP archive format, full /
incremental / differential backup chains, ZIP merge command, configuration
schema, directory layout, latest-log capture, storage limits, and message flow.

## Platform mappings

| Upstream NeoForge facility | Fabric implementation |
| --- | --- |
| `@Mod` constructor | `ModInitializer` entrypoint in `fabric.mod.json` |
| Dist/client registration | separate `src/client` source set and `ClientModInitializer` |
| event bus callbacks | Fabric command, server tick, join, and disconnect callbacks |
| custom payload registration/send | Fabric play networking APIs |
| client GUI layer | Fabric HUD element registry |
| access transformer | named-namespace Fabric access widener remapped by Loom |
| `FMLTranslations` | bundled English server-translation adapter |
| NeoForge configuration | Forge Config API Port on Fabric |

Forge Config API Port intentionally exposes compatible `net.neoforged` config
types and the configuration screen. Those imports preserve the TOML schema and
do not introduce a NeoForge loader or event dependency.

## Validation status

This section is updated only from observed results. A successful compilation
alone is not sufficient to mark the version complete.

- Build: passed with Gradle 9.5.1 and Loom 1.17.19
- Dedicated server: started Minecraft 1.21.11 with no optional compatibility mods
- Automatic backup: passed when the test world opened with no players online
- Manual backup: passed with `/simplebackups backup start`
- Configuration: generated and hot-reloaded from the preserved TOML schema
- Client smoke test: reached resource loading and the main-menu runtime without a
  dedicated-server classloading failure

One-time bring-up validation also produced an incremental child ZIP and a merged
ZIP containing the changed test file. These deeper checks established the port
pattern but are not required for every future version-line build unless the
affected code changes or a regression is reported.
