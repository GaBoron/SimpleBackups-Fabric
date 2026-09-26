# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · **Русский** · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

Неофициальный, поддерживаемый сообществом порт
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) для Fabric. Он
сохраняет поведение автоматического и ручного резервного копирования миров из
соответствующей версии upstream, заменяя интеграцию Forge и NeoForge на Fabric.

**[Скачать на Modrinth](https://modrinth.com/mod/simple-backups-for-fabric)**

> [!IMPORTANT]
> Это не официальная версия для Fabric от авторов SimpleBackups. Сообщайте о
> проблемах порта для Fabric в этом репозитории, а не в upstream-проекте.

## Загрузки

Выберите выпуск для вашей версии Minecraft. Каждая версия ведёт на свою страницу Modrinth; JAR также доступен в этом репозитории.

| Minecraft / Порт | Обязательные моды Fabric | Java | Загрузка |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/1D2p0tsa)<br>[JAR на GitHub](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/syZmhGME)<br>[JAR на GitHub](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/YM9ibGbj)<br>[JAR на GitHub](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/wTtU5OXZ)<br>[JAR на GitHub](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |

Ветки исходного кода: [`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) и
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1).

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
