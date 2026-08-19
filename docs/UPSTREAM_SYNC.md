# Upstream synchronization workflow

An upstream update should be a sync, not a fresh rewrite.

## Existing Minecraft line

1. Fetch the upstream branch and record its new tip/tag in the version matrix and
   migration record.
2. Review upstream-only changes from the previous recorded commit, separating
   core backup changes from NeoForge/Forge platform changes.
3. Apply core changes with minimal modification to the matching Fabric branch.
4. Reapply only the platform mappings affected by the upstream diff.
5. Compare configuration keys/defaults, archive metadata, directory structure,
   commands, messages, and compatibility adapters against upstream.
6. Run the full acceptance checks below before updating port status or releasing.

## New Minecraft line

1. Create a new `fabric/<upstream-branch>` branch from the new upstream
   branch/tag/commit.
2. Port the established seams: initialization, callbacks, commands, networking,
   configuration, client source separation, paths, and optional integrations.
3. Keep version-specific Fabric APIs in that branch. Do not add cross-version
   conditionals to an older branch solely to share adapter code.
4. Record the exact upstream relationship before publishing.

## Acceptance checks for every completed line

- clean build with that line's Java, Loom, Loader, and Fabric API versions
- Fabric client reaches the title screen or an integrated world
- dedicated server reaches `Done` without loading client-only classes
- TOML configuration is generated and read with upstream-compatible semantics
- scheduled and manual backup commands create archives
- full and incremental/differential chains behave like the matching upstream line
- merge/read path retains files from the newest child archive
- every advertised compression format can write and read/merge
- networking is safe when the client lacks the payload receiver and on dedicated
  servers
- optional compatibility mods may be absent without blocking startup
- release JAR contains required license and attribution files

Only after all applicable checks pass should a line move from Planned or WIP to
Complete in the matrix.
