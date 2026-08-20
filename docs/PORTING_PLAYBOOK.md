# Fabric porting playbook

This document records the repeatable lessons from completed SimpleBackups
Fabric version lines. It describes a migration method, not a shared runtime
compatibility layer.

## Start from the matching upstream source

1. Fetch and pin the exact upstream branch, tag, and commit.
2. Confirm the published SimpleBackups and Minecraft versions independently.
3. Create `fabric/<upstream-branch>` from that source.
4. Record the relationship before changing platform code.

Never create an older port by copying the newest Fabric tree. Copying a proven
adapter idea is safe; copying newer Minecraft APIs, formats, configuration keys,
or user-visible behavior is not.

## Classify every change

Separate the diff into three groups before implementation:

- Upstream core: backup selection, chains, archive I/O, merge, file filtering,
  storage limits, naming, messages, and configuration semantics.
- Minecraft-version API: saved data, world paths, rendering, permissions,
  payload codecs, and lifecycle signatures.
- Loader platform: initialization, callbacks, networking registration, client
  source separation, configuration registration, mod detection, and metadata.

Preserve the first group by default. Keep the other two groups inside narrow,
version-local adapters and composition entrypoints.

## Established Fabric mappings

- Keep the common initializer thin: register configuration, payload types, and
  lifecycle callbacks only.
- Put all physical-client classes in Loom's client source set.
- Check `canSend` before sending an optional payload to a client.
- Use Forge Config API Port when it preserves the upstream TOML schema better
  than translating configuration into a different format.
- Isolate optional compatibility through Fabric Loader detection and guarded
  reflection when a stable compile-time Fabric API is unavailable.
- Replace FML server translations with a small adapter over bundled `en_us`.
- Replace access transformers with the smallest official-namespace access
  widener; remove entries no longer required by Fabric networking.
- Minecraft 1.21.11 is the last obfuscated release. With Loom 1.14 or newer, use
  the `net.fabricmc.fabric-loom-remap` plugin plus
  `loom.officialMojangMappings()` for that line. Use the non-remap
  `net.fabricmc.fabric-loom` plugin for unobfuscated 26.x lines. The plugin ID is
  a version boundary, not a preference.
- On remapped branches, declare Fabric Loader, Fabric API, and Fabric mod
  dependencies with `modImplementation` so Loom remaps their intermediary
  classes. Plain `implementation` is appropriate only for ordinary libraries.
- Keep version-line Java syntax period-correct. Java 21 branches must use named
  lambda parameters instead of the `_` unnamed variables accepted by later Java
  toolchains.
- Inspect Minecraft class signatures rather than copying NeoForge conveniences.
  For example, NeoForge may add a `SavedDataType` context constructor that is not
  present in the Fabric/vanilla class for the same Minecraft version.

## Validate efficiently

The routine gate for each version line is deliberately small:

1. Build and remap the release JAR.
2. Start a world and observe a successful automatic backup.
3. Run `/simplebackups backup start` and observe a successful manual backup.

Inspect metadata and attribution as a cheap packaging check. Reserve incremental,
differential, merge, compression, restore, and full client-interaction tests for
initial platform bring-up, changes touching those paths, or reported regressions.
This keeps historical-version work proportional without treating compilation
alone as functional validation.
