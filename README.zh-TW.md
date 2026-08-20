# SimpleBackups Fabric 版

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · **繁體中文**

這是 [SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) 的非官方、
由社群維護的 Fabric 移植版。專案在保留對應上游版本的排程與手動世界備份行為
的同時，將 Forge 與 NeoForge 平台整合替換為 Fabric 實作。

> [!IMPORTANT]
> 本專案不是 SimpleBackups 原作者發布的官方 Fabric 版本。Fabric 移植版的
> 問題請回報到本儲存庫，不要向上游專案回報。

## 下載

請選擇與你的 Minecraft 版本完全相符的 JAR。

| Minecraft / 移植版本 | 必要的 Fabric 前置 Mod | Java | 下載 / 原始碼 |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | Java 25+ | [下載 JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar)<br>[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | Java 25+ | [下載 JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar)<br>[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | Java 21+ | [下載 JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar)<br>[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) |

每個原始碼分支都以對應的 SimpleBackups 上游分支為基礎，並且可以獨立建置。
此預設分支只保存已發布的建置成品與專案層級說明。

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
