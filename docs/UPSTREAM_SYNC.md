# Upstream synchronization workflow

An upstream update should be a sync, not a fresh rewrite.

## Existing Minecraft line

1. Fetch the upstream branch and record its new tip or release version.
2. Review upstream changes since the previously recorded commit.
3. Apply core changes with minimal modification to the matching Fabric branch.
4. Reapply only platform mappings affected by the upstream diff.
5. Compare configuration, archive metadata, directories, commands, messages,
   and optional compatibility against upstream.
6. Repeat the acceptance checks in the porting playbook.

## New Minecraft line

1. Create a new `fabric/<upstream-branch>` from the new upstream branch, tag, or
   pinned release commit.
2. Reassess Minecraft and Fabric APIs before copying any adapter implementation.
3. Keep version-specific APIs in that branch rather than adding cross-version
   runtime conditionals.
4. Record exact upstream and dependency versions before publication.

See [the Fabric porting playbook](PORTING_PLAYBOOK.md) for the platform map and
validation sequence.
