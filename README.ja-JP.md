# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · **日本語** · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) の非公式な
コミュニティ管理 Fabric 移植版です。対応する上流バージョンの自動および
手動ワールドバックアップの動作を維持しつつ、Forge / NeoForge 固有の
統合部分を Fabric 向けに置き換えています。

**[Modrinth からダウンロード](https://modrinth.com/mod/simple-backups-for-fabric)**

> [!IMPORTANT]
> このプロジェクトは SimpleBackups 作者による公式 Fabric 版ではありません。
> Fabric 移植版の問題は上流ではなく、このリポジトリへ報告してください。

## ダウンロード

Minecraft のバージョンに合うリリースを選んでください。各バージョンから Modrinth の公開ページを開けます。JAR はこのリポジトリからも入手できます。

| Minecraft / 移植版 | 必須 Fabric Mod | Java | ダウンロード |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/1D2p0tsa)<br>[GitHub の JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/syZmhGME)<br>[GitHub の JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/YM9ibGbj)<br>[GitHub の JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [Modrinth](https://modrinth.com/mod/simple-backups-for-fabric/version/wTtU5OXZ)<br>[GitHub の JAR](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |

ソースブランチ: [`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2)、
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1)、
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x)、
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1)。

## インストール

1. Minecraft の対象バージョンに合う Fabric Loader をインストールします。
2. 対応する Fabric API と Forge Config API Port をインストールします。
3. SimpleBackups Fabric の JAR と依存 Mod を `mods` フォルダーへ入れます。
4. ゲームまたは専用サーバーを一度起動し、設定ファイルを生成します。

必要な依存関係のバージョンは各 JAR の `fabric.mod.json` に記録されています。
必須 Mod がない場合や互換性がない場合は、Fabric Loader が依存関係エラーを
表示します。

## 機能

- ワールド実行中の定期バックアップ
- `/simplebackups backup start` コマンドによる手動バックアップ
- 完全、増分、差分バックアップモード
- 既存の SimpleBackups 設定名とディレクトリ構成
- バックアップチェーンのマージと保存容量制限
- 専用サーバーと統合サーバーのサポート
- 対象 Mod がない場合でも安全なオプション互換機能

アーカイブ形式と詳細オプションは、対応する上流バージョンに従います。
たとえば 1.21.11 系列は当時の ZIP 専用動作を維持し、新しい系列では対応する
上流ソースが備える圧縮形式を利用できます。

## 帰属とライセンス

SimpleBackups は上流プロジェクトとその貢献者によって作成されました。
この Fabric 移植版は上流の Apache License 2.0 と帰属表示を維持しています。
[LICENSE](LICENSE) と [NOTICE](NOTICE) を参照してください。
