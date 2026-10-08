# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · [Português (Brasil)](README.pt-BR.md) · [Русский](README.ru-RU.md) · **Türkçe** · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups) modunun topluluk
tarafından sürdürülen, resmî olmayan Fabric portudur. İlgili upstream sürümünün
zamanlanmış ve elle başlatılan dünya yedekleme davranışını korurken Forge ve
NeoForge platform entegrasyonunu Fabric ile değiştirir.

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> Bu proje, SimpleBackups geliştiricilerinin resmî Fabric sürümü değildir.
> Fabric portuyla ilgili sorunları upstream projeye değil, bu depoya bildirin.

## İndirmeler

Minecraft sürümünüze uygun yayını seçin. Tabloda GitHub JAR bağlantıları bulunur; yukarıdaki Modrinth rozeti orada yayımlanan sürümlere götürür.

### Standart Fabric sürümleri

| Minecraft / Port | Gerekli Fabric modları | Java | İndirme |
| --- | --- | --- | --- |
| **26.3**<br>SimpleBackups Fabric 26.3.0 | Fabric Loader ≥ 0.19.5<br>Fabric API ≥ 0.161.0+26.3<br>Forge Config API Port ≥ 26.3.1 | 25+ | [GitHub JAR](artifacts/26.3/simplebackups-fabric-26.3.0.jar) |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [GitHub JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [GitHub JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [GitHub JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [GitHub JAR](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [GitHub JAR](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

Kaynak kodu dalları: [`fabric/26.3`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3),
[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x),
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1) ve
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x).

### GUI önizleme sürümü

Tek oyunculu dünya seçme ekranına yedekleri geri yükleme düğmesi ekler. Standart
sürümle önceden oluşturulmuş ZIP, ZSTD ve SBK biçimindeki tam, artımlı ve
diferansiyel yedekleri destekler. Yedeği özgün adı ve yedekleme zamanı ile
adlandırılan yeni bir dünya olarak geri yükleyebilir veya özgün dünyayı koruyarak
seçili dünyayı değiştirebilirsiniz.

| Minecraft | GUI sürümü / kaynak kodu | İndirme |
| --- | --- | --- |
| **26.3** | [26.3.0-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3-gui) | [GitHub JAR](artifacts/26.3-gui/simplebackups-fabric-26.3.0-gui.jar) |
| **26.1.x (26.1–26.1.2)** | [26.1.5-gui](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1-gui) | [GitHub JAR](artifacts/26.1-gui/simplebackups-fabric-26.1.5-gui.jar) |

Fabric modları ve Java gereksinimleri yukarıdaki ilgili sürümle aynıdır.
Standart sürümün JAR dosyasını bu dosyayla değiştirin; `mods` klasöründe yalnızca
bir SimpleBackups Fabric JAR dosyası bulunsun.

## Kurulum

1. Minecraft sürümünüze uygun Fabric Loader'ı kurun.
2. Uygun Fabric API ve Forge Config API Port sürümlerini kurun.
3. SimpleBackups Fabric JAR dosyasını ve bağımlılıklarını `mods` klasörüne koyun.
4. Yapılandırma dosyalarının oluşturulması için oyunu veya özel sunucuyu bir kez başlatın.

Gerekli bağımlılık sürümleri her JAR içindeki `fabric.mod.json` dosyasında
belirtilir. Gerekli bir mod eksik veya uyumsuzsa Fabric Loader açık bir bağımlılık
hatası gösterir.

## Özellikler

- Dünya çalışırken zamanlanmış yedeklemeler
- `/simplebackups backup start` komutuyla elle yedekleme
- Tam, artımlı ve diferansiyel yedekleme modları
- Mevcut SimpleBackups yapılandırma adları ve dizin yapısı
- Yedekleme zincirlerini birleştirme ve depolama sınırı yönetimi
- Özel ve tümleşik sunucu desteği
- İlgili modlar yokken güvenle yalıtılan isteğe bağlı uyumluluk entegrasyonları

Arşiv biçimleri ve gelişmiş seçenekler ilgili upstream sürümünü izler. Örneğin
1.21.11 dalı dönemine uygun yalnızca ZIP davranışını korurken, yeni sürüm dalları
eşleşen upstream kaynak kodunun desteklediği sıkıştırma biçimlerini içerir.

## Atıf ve lisans

SimpleBackups, upstream proje ve katkıda bulunanlar tarafından oluşturulmuştur.
Bu Fabric portu upstream Apache License 2.0 lisansını ve atıfları korur;
[LICENSE](LICENSE) ve [NOTICE](NOTICE) dosyalarına bakın.
