# Version matrix and branch policy

Audit date: 2026-08-19.

The audit used the upstream Git branches and tags plus the published versions on
[Modrinth](https://modrinth.com/mod/simple-backups/versions). The upstream GitHub
repository had no GitHub Releases at the audit date; Modrinth and CurseForge are
the release channels. Only the 26.x releases currently have Git tags.

## Supported upstream lines

| Minecraft versions | Published upstream SimpleBackups line | Original loader | Upstream source baseline | Fabric branch | Port status |
| --- | --- | --- | --- | --- | --- |
| 1.18.1 | 1.0.0–1.0.1 | Forge | history preceding `1.18.x` | `fabric/1.18.x` | Planned; retain only if a reproducible historical baseline is confirmed |
| 1.18.2 | 1.1.x (latest 1.1.18) | Forge | `upstream/1.18.x` | `fabric/1.18.x` | Planned |
| 1.19 | 2.0.x (latest 2.0.2) | Forge | early history of `upstream/1.19.2` | `fabric/1.19.2` | Planned; pin the exact source commit before work starts |
| 1.19.1–1.19.3 | 2.1.x (latest 2.1.15) | Forge | `upstream/1.19.2` | `fabric/1.19.2` | Planned |
| 1.19.4 | 2.2.x (latest 2.2.11) | Forge | `upstream/1.19.x` | `fabric/1.19.x` | Planned |
| 1.20.1 | 3.1.x (latest 3.1.24) | Forge / NeoForge | `upstream/1.20.x` | `fabric/1.20.x` | Planned; high-value historical line |
| 1.21–1.21.4 | 1.21-4.0.x (latest 1.21-4.0.30) | NeoForge | `upstream/1.21.1` | `fabric/1.21.1` | Planned; high-value historical line |
| 1.21.5 | 21.5.x (latest 21.5.8) | NeoForge | history between `1.21.1` and `1.21.6` | `fabric/1.21.5` | Planned; exact commit must be pinned first |
| 1.21.6–1.21.8 | 21.6.x (latest 21.6.13) | NeoForge | `upstream/1.21.6` | `fabric/1.21.6` | Planned |
| 1.21.9–1.21.10 | 21.9.x (latest 21.9.10) | NeoForge | `upstream/1.21.9` | `fabric/1.21.9` | Planned |
| 1.21.11 | 21.11.x (latest 21.11.6) | NeoForge | `upstream/1.21.x` | `fabric/1.21.x` | Planned; high-value historical line |
| 26.1–26.1.2 | 26.1.x (latest/tag 26.1.5) | NeoForge | `upstream/26.1`, tag `26.1.5` | `fabric/26.1` | Complete and locally validated |
| 26.2 | 26.2.x (latest/tag 26.2.1) | NeoForge | `upstream/26.2` at `f81e496`, functional tag `26.2.1` at `127f376` | `fabric/26.2` | Complete and locally validated |
| Future upstream versions | Future matching line | Upstream loader | New upstream branch/tag | matching `fabric/<line>` | Follow upstream |

“Complete” applies to one version line only after its build, client, dedicated
server, automatic/manual backup, configuration, networking, archive, and merge
checks pass. A row marked Planned is not advertised as supported.

## Branch rules

1. Create each Fabric line from its matching upstream branch or release commit,
   not from the newest Fabric branch.
2. Use `fabric/<upstream-branch>` where an upstream branch exists. If a formally
   published line has no surviving branch, use its Minecraft line only after the
   exact upstream commit has been recorded.
3. Keep platform-port commits separate from later upstream-sync commits so that
   `git diff <upstream-ref>...fabric/<line>` remains useful.
4. Do not merge all Minecraft versions into a shared compatibility branch.
5. Backport only proven Fabric adapter patterns; keep Minecraft API differences
   local to each line.

## Prioritization

With 26.2 and 26.1 complete, prefer 1.21.11, 1.21.1, and 1.20.1. They cover
recent API transitions and long-lived player bases without pretending that every
published historical point release is already supported. Older lines remain
valuable, but start only from their period-correct upstream source.
