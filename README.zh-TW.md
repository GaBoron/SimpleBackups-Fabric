# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · **繁體中文**

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

這是 [SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) 的非官方、
由社群維護的 Fabric 移植版。專案在保留對應上游版本的排程與手動世界備份行為
的同時，將 Forge 與 NeoForge 平台整合替換為 Fabric 實作。

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> 本專案不是 SimpleBackups 原作者發布的官方 Fabric 版本。Fabric 移植版的
> 問題請回報到本儲存庫，不要向上游專案回報。

## 下載

請選擇與你的 Minecraft 版本相符的發布版本。表格提供 GitHub JAR；上方的 Modrinth 徽章可查看已在 Modrinth 發布的版本。

### 一般 Fabric 移植版

| Minecraft / 移植版本 | 必要的 Fabric 前置 Mod | Java | 下載 |
| --- | --- | --- | --- |
| **26.3**<br>SimpleBackups Fabric 26.3.0 | Fabric Loader ≥ 0.19.5<br>Fabric API ≥ 0.161.0+26.3<br>Forge Config API Port ≥ 26.3.1 | 25+ | [GitHub JAR](artifacts/26.3/simplebackups-fabric-26.3.0.jar) |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [GitHub JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [GitHub JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [GitHub JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [GitHub JAR](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [GitHub JAR](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

原始碼分支：[`fabric/26.3`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3)、
[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2)、
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1)、
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x)、
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1)、
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x)。

### GUI 預覽版

在單人遊戲的世界選擇介面新增**還原備份**按鈕，可讀取一般移植版既有的
ZIP、ZSTD、SBK 完整、增量與差異備份。可以還原為以「原名-備份時間」命名的新世界，
也可以取代目前世界。覆蓋前會依設定的格式壓縮備份原世界，在同一清單中標記為
**回退**，遵循一般備份的保留數量與空間限制。

| Minecraft | GUI 版本 / 原始碼 | 下載 |
| --- | --- | --- |
| **26.3** | [26.3.0-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3-gui) | [GitHub JAR](artifacts/26.3-gui/simplebackups-fabric-26.3.0-gui.jar) |
| **26.1.x (26.1–26.1.2)** | [26.1.5-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1-gui) | [GitHub JAR](artifacts/26.1-gui/simplebackups-fabric-26.1.5-gui.jar) |

各 GUI 版本的前置 Mod 與 Java 要求和上方對應的一般移植版相同。安裝時以 GUI JAR 取代一般移植版，
`mods` 中只保留一個 SimpleBackups Fabric JAR。

## 安裝

1. 安裝與 Minecraft 版本相符的 Fabric Loader。
2. 安裝表格中對應版本的 Fabric API 與 Forge Config API Port。
3. 將 SimpleBackups Fabric JAR 及其前置 Mod 放入 `mods` 資料夾。
4. 啟動一次遊戲或專用伺服器，以產生設定檔。

每個 JAR 的 `fabric.mod.json` 都會宣告所需的依賴版本。如果必要的 Mod
缺少或版本不相容，Fabric Loader 會顯示明確的依賴錯誤。

## 功能

- 世界執行時按排程自動備份
- 使用 `/simplebackups backup start` 指令手動備份
- 完整、增量與差異備份模式
- 保留 SimpleBackups 原有的設定名稱與目錄結構
- 備份鏈合併與儲存空間限制
- 支援專用伺服器與整合伺服器
- 未安裝相應 Mod 時安全隔離選用相容功能

壓縮格式與進階選項以對應的上游版本為準。例如，1.21.11 版本線保留其當時
僅使用 ZIP 的行為；較新的版本線則包含對應上游原始碼支援的壓縮格式。

## 著作權與授權

SimpleBackups 由上游專案及其貢獻者建立。本 Fabric 移植版保留上游的
Apache License 2.0 與原始歸屬資訊，詳情請參閱 [LICENSE](LICENSE) 與
[NOTICE](NOTICE)。
