# Licensing and Modrinth publication policy

Simple Backups Fabric is a license-abiding, community-maintained Fabric port of
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups), not an official
release from the upstream authors.

## Source changes

- Keep the upstream Apache License 2.0 license, copyright, attribution, and
  existing notices.
- Every distributed source file modified from upstream must carry a prominent
  notice that it was changed by the Simple Backups Fabric project, including the
  year of modification.
- Add the same notice when a later upstream source file is first adapted for
  Fabric. Do not remove it during upstream synchronization.
- New files written specifically for this port must not falsely claim to be
  modified upstream files. They remain covered by this repository's license.
- Do not distribute an unchanged upstream binary as a Fabric-port release.

## Project identity

- Use the project name **Simple Backups Fabric**.
- State clearly that this is an unofficial, community-maintained Fabric port and
  link to the upstream project.
- Keep the port icon visibly distinct. The current icon combines the upstream
  SimpleBackups artwork with a Fabric logo badge.
- Project links, issue links, and support directions must point to this port
  where appropriate. Do not imply endorsement or support by upstream.

## Modrinth release checklist

Before submitting or updating a Modrinth project or version:

1. Confirm that the release is a substantial Fabric adaptation rather than a
   direct reupload, and that distribution complies with Apache License 2.0.
2. Use accurate license, client/server environment, game-version, loader, and
   dependency metadata. Declare every required dependency on the version.
3. Keep the title to the project name and provide an honest English description
   of what the port does, why it is useful, and critical installation details.
4. Use only public, relevant external links, including this repository and the
   upstream attribution link.
5. Upload each Minecraft/loader build as its own version. Use additional files
   only for permitted companion artifacts such as a generated sources JAR.
6. Recheck the current
   [Modrinth Content Rules](https://modrinth.com/legal/rules) before publication,
   because platform requirements can change.
