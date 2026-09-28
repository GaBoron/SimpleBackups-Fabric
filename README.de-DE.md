# Simple Backups Fabric

[English](README.md) · **Deutsch** · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

Ein inoffizieller, von der Community gepflegter Fabric-Port von
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). Er bewahrt das
Verhalten der entsprechenden Upstream-Version für geplante und manuell
ausgelöste Welt-Backups und ersetzt die Forge- und NeoForge-Integration durch
Fabric.

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> Dieses Projekt ist keine offizielle Fabric-Veröffentlichung der
> SimpleBackups-Autoren. Bitte melde Probleme mit dem Fabric-Port in diesem
> Repository und nicht beim Upstream-Projekt.

## Downloads

Wähle die Veröffentlichung, die zu deiner Minecraft-Version passt. Die Tabelle verlinkt GitHub-JARs; über das Modrinth-Abzeichen oben findest du die dort veröffentlichten Versionen.

| Minecraft / Port | Erforderliche Fabric-Mods | Java | Download |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [GitHub-JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [GitHub-JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [GitHub-JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [GitHub-JAR](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [GitHub-JAR](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

Quellcode-Branches: [`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x),
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1) und
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x).

## Installation

1. Installiere den Fabric Loader für die passende Minecraft-Version.
2. Installiere die passenden Versionen von Fabric API und Forge Config API Port.
3. Lege die SimpleBackups-Fabric-JAR und ihre Abhängigkeiten im Ordner `mods` ab.
4. Starte das Spiel oder den dedizierten Server einmal, damit die
   Konfigurationsdateien erzeugt werden.

Die erforderlichen Abhängigkeitsversionen stehen in der `fabric.mod.json` jeder
JAR. Fabric Loader zeigt einen eindeutigen Abhängigkeitsfehler an, wenn ein Mod
fehlt oder nicht kompatibel ist.

## Funktionen

- Geplante Backups, während eine Welt läuft
- Manuelle Backups mit `/simplebackups backup start`
- Vollständige, inkrementelle und differenzielle Backup-Modi
- Bestehende SimpleBackups-Konfigurationsnamen und Verzeichnisstruktur
- Zusammenführen von Backup-Ketten und Begrenzung des Speicherverbrauchs
- Unterstützung für dedizierte und integrierte Server
- Sicher isolierte optionale Kompatibilitätsintegrationen

Archivformate und erweiterte Optionen entsprechen der jeweiligen
Upstream-Version. Die Version für 1.21.11 behält beispielsweise das damalige
reine ZIP-Format, während neuere Versionszweige die Kompressionsformate ihrer
jeweiligen Upstream-Version unterstützen.

## Namensnennung und Lizenz

SimpleBackups wurde vom Upstream-Projekt und seinen Mitwirkenden entwickelt.
Dieser Fabric-Port behält die Apache License 2.0 und die ursprüngliche
Namensnennung bei; siehe [LICENSE](LICENSE) und [NOTICE](NOTICE).
