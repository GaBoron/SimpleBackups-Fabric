# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · **Português (Brasil)** · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<p align="center">
  <img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">
</p>

Uma versão não oficial para Fabric, mantida pela comunidade, do
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). Ela preserva o
comportamento de backups automáticos e manuais de mundos da versão upstream
correspondente, substituindo a integração específica de Forge e NeoForge por
uma implementação para Fabric.

[![Modrinth downloads](https://img.shields.io/modrinth/dt/O8REYcgj?logo=modrinth&label=Modrinth%20downloads)](https://modrinth.com/mod/simple-backups-for-fabric) [![Minecraft Versions](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2FO8REYcgj&query=%24.game_versions&label=Minecraft&logo=minecraft&color=62B47A)](https://modrinth.com/mod/simple-backups-for-fabric/versions)

> [!IMPORTANT]
> Este projeto não é uma versão oficial para Fabric criada pelos autores do
> SimpleBackups. Relate problemas desta versão para Fabric neste repositório,
> e não ao projeto upstream.

## Downloads

Escolha a versão correspondente à sua versão do Minecraft. A tabela contém os JARs no GitHub; o selo do Modrinth acima mostra as versões publicadas lá.

### Versões padrão para Fabric

| Minecraft / Port | Mods Fabric obrigatórios | Java | Download |
| --- | --- | --- | --- |
| **26.3**<br>SimpleBackups Fabric 26.3.0 | Fabric Loader ≥ 0.19.5<br>Fabric API ≥ 0.161.0+26.3<br>Forge Config API Port ≥ 26.3.1 | 25+ | [JAR no GitHub](artifacts/26.3/simplebackups-fabric-26.3.0.jar) |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | 25+ | [JAR no GitHub](artifacts/26.2/simplebackups-fabric-26.2.1.jar) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | 25+ | [JAR no GitHub](artifacts/26.1/simplebackups-fabric-26.1.5.jar) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | 21+ | [JAR no GitHub](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar) |
| **1.21.1**<br>SimpleBackups Fabric 4.0.30 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.116.15+1.21.1<br>Forge Config API Port ≥ 21.1.1 | 21+ | [JAR no GitHub](artifacts/1.21.1/simplebackups-fabric-4.0.30.jar) |
| **1.20.1**<br>SimpleBackups Fabric 3.1.25 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.92.12+1.20.1<br>Forge Config API Port ≥ 8.0.0 | 17+ | [JAR no GitHub](artifacts/1.20.1/simplebackups-fabric-3.1.25.jar) |

Branches do código-fonte: [`fabric/26.3`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3),
[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2),
[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1),
[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x),
[`fabric/1.21.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.1) e
[`fabric/1.20.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.20.x).

### Prévia da versão com GUI

**26.3.0-gui · Minecraft 26.3 · Fabric**

Adiciona um botão de restauração de backups à seleção de mundos no modo de um
jogador. Permite restaurar backups completos, incrementais e diferenciais já
criados pela versão padrão nos formatos ZIP, ZSTD e SBK. Restaure como um novo
mundo com o nome original e a data e hora do backup, ou substitua o mundo
selecionado mantendo uma cópia do original.

[Baixar JAR com GUI](artifacts/26.3-gui/simplebackups-fabric-26.3.0-gui.jar) · [Código-fonte e uso](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.3-gui)

Os requisitos de mods Fabric e Java são os mesmos da versão para 26.3 acima.
Substitua o JAR da versão padrão por este e mantenha apenas um JAR do
SimpleBackups Fabric na pasta `mods`.

## Instalação

1. Instale o Fabric Loader correspondente à versão do Minecraft.
2. Instale as versões correspondentes do Fabric API e do Forge Config API Port.
3. Coloque o JAR do SimpleBackups Fabric e suas dependências na pasta `mods`.
4. Inicie o jogo ou o servidor dedicado uma vez para gerar os arquivos de configuração.

As versões obrigatórias das dependências estão declaradas no `fabric.mod.json`
de cada JAR. O Fabric Loader exibirá um erro claro se algum mod obrigatório
estiver ausente ou for incompatível.

## Recursos

- Backups programados enquanto um mundo está em execução
- Backups manuais pelo comando `/simplebackups backup start`
- Modos de backup completo, incremental e diferencial
- Nomes de configuração e estrutura de diretórios existentes do SimpleBackups
- Mesclagem de cadeias de backup e controle do limite de armazenamento
- Suporte a servidores dedicados e integrados
- Integrações opcionais isoladas com segurança quando seus mods não estão presentes

Os formatos de arquivo e as opções avançadas seguem a versão upstream
correspondente. Por exemplo, a linha 1.21.11 mantém o comportamento original
somente com ZIP, enquanto versões mais recentes incluem os formatos de
compactação suportados pelo respectivo código upstream.

## Créditos e licença

O SimpleBackups foi criado pelo projeto upstream e seus colaboradores. Esta
versão para Fabric preserva a Apache License 2.0 e os créditos originais;
consulte [LICENSE](LICENSE) e [NOTICE](NOTICE).
