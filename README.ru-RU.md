# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · **Русский** · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

Неофициальный, поддерживаемый сообществом порт
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) для Fabric. Он
сохраняет поведение автоматического и ручного резервного копирования миров из
соответствующей версии upstream, заменяя интеграцию Forge и NeoForge на Fabric.

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> Это не официальная версия для Fabric от авторов SimpleBackups. Сообщайте о
> проблемах порта для Fabric в этом репозитории, а не в upstream-проекте.

## Загрузки

Выберите выпуск для вашей версии Minecraft. В таблице доступны JAR на GitHub; значок Modrinth выше ведёт к версиям, опубликованным там.

| Minecraft / Порт | Обязательные моды Fabric | Java | Загрузка |
| --- | --- | --- | --- |
| **26.3**<br>SimpleBackups Fabric 26.3.0 | Fabric Loader ≥ 0.19.5<br>Fabric API ≥ 0.161.0+26.3<br>Forge Config API Port ≥ 26.3.1 | 25+ | [JAR на GitHub](artifacts/26.3/simplebackups-fabric-26.3.0.jar) |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [JAR на GitHub](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [JAR на GitHub](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [JAR на GitHub](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [JAR на GitHub](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [JAR на GitHub](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

Ветки исходного кода: [`fabric/26.3`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3),
[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x),
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1) и
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x).

## Установка

1. Установите Fabric Loader для нужной версии Minecraft.
2. Установите соответствующие версии Fabric API и Forge Config API Port.
3. Поместите JAR SimpleBackups Fabric и его зависимости в папку `mods`.
4. Один раз запустите игру или выделенный сервер для создания файлов конфигурации.

Точные версии зависимостей указаны в `fabric.mod.json` внутри каждого JAR.
Fabric Loader покажет понятную ошибку зависимостей, если обязательный мод
отсутствует или несовместим.

## Возможности

- Автоматическое резервное копирование во время работы мира
- Ручное резервное копирование командой `/simplebackups backup start`
- Полный, инкрементный и дифференциальный режимы
- Существующие имена настроек и структура каталогов SimpleBackups
- Слияние цепочек резервных копий и ограничение занимаемого места
- Поддержка выделенного и встроенного серверов
- Безопасная изоляция необязательных интеграций при отсутствии нужных модов

Форматы архивов и дополнительные параметры соответствуют выбранной upstream-
версии. Например, ветка 1.21.11 сохраняет характерный для неё режим только ZIP,
а новые ветки включают форматы сжатия, поддерживаемые соответствующим upstream-
кодом.

## Авторство и лицензия

SimpleBackups создан upstream-проектом и его участниками. Этот порт для Fabric
сохраняет Apache License 2.0 и исходное указание авторства; см. [LICENSE](LICENSE)
и [NOTICE](NOTICE).
