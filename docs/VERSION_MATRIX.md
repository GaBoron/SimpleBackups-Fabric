# Version matrix and branch policy

Audit date: 2026-08-20.

| Minecraft versions | Upstream SimpleBackups line | Upstream source | Fabric branch | Status |
| --- | --- | --- | --- | --- |
| 1.18.1 | 1.0.x | exact historical commit still required | `fabric/1.18.x` | Planned |
| 1.18.2 | 1.1.x | `upstream/1.18.x` | `fabric/1.18.x` | Planned |
| 1.19–1.19.3 | 2.0.x / 2.1.x | `upstream/1.19.2` history | `fabric/1.19.2` | Planned |
| 1.19.4 | 2.2.x | `upstream/1.19.x` | `fabric/1.19.x` | Planned |
| 1.20.1 | 3.1.x | `upstream/1.20.x` | `fabric/1.20.x` | Planned; high value |
| 1.21–1.21.4 | 1.21-4.0.x | `upstream/1.21.1` | `fabric/1.21.1` | Planned; high value |
| 1.21.5 | 21.5.x | exact commit still required | `fabric/1.21.5` | Planned |
| 1.21.6–1.21.8 | 21.6.x | `upstream/1.21.6` | `fabric/1.21.6` | Planned |
| 1.21.9–1.21.10 | 21.9.x | `upstream/1.21.9` | `fabric/1.21.9` | Planned |
| 1.21.11 | 21.11.x (`21.11.6`) | `upstream/1.21.x` at `17430e2` | `fabric/1.21.x` | Complete locally |
| 26.1–26.1.2 | 26.1.x (`26.1.5`) | `upstream/26.1` | `fabric/26.1` | Complete locally |
| 26.2 | 26.2.x (`26.2.1`) | `upstream/26.2` | `fabric/26.2` | Complete |

Each Fabric branch starts from the matching upstream source and remains
independently buildable. The routine completion gate is a successful build plus
observed automatic and manual backups in a started world. Deeper feature tests
are targeted when that code changes or a regression is reported.
