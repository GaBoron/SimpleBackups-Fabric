# Simple Backups Fabric

[English](README.md) · [Deutsch](README.de-DE.md) · [日本語](README.ja-JP.md) · **Português (Brasil)** · [Русский](README.ru-RU.md) · [Türkçe](README.tr-TR.md) · [简体中文](README.zh-CN.md) · [繁體中文](README.zh-TW.md)

<img src="assets/simplebackups/icon.png" alt="Simple Backups Fabric icon" width="160">

Uma versão não oficial para Fabric, mantida pela comunidade, do
[SimpleBackups](https://github.com/ChaoticTrials/SimpleBackups). Ela preserva o
comportamento de backups automáticos e manuais de mundos da versão upstream
correspondente, substituindo a integração específica de Forge e NeoForge por
uma implementação para Fabric.

> [!IMPORTANT]
> Este projeto não é uma versão oficial para Fabric criada pelos autores do
> SimpleBackups. Relate problemas desta versão para Fabric neste repositório,
> e não ao projeto upstream.

## Downloads

Escolha o arquivo JAR que corresponda exatamente à sua versão do Minecraft.

| Minecraft / Port | Mods Fabric obrigatórios | Java | Download / Código-fonte |
| --- | --- | --- | --- |
| **26.2**<br>SimpleBackups Fabric 26.2.1 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.158.0+26.2<br>Forge Config API Port ≥ 26.2.1 | Java 25+ | [Baixar JAR](artifacts/26.2/simplebackups-fabric-26.2.1.jar)<br>[`fabric/26.2`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.2) |
| **26.1–26.1.2**<br>SimpleBackups Fabric 26.1.5 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.149.1+26.1.2<br>Forge Config API Port ≥ 26.1.5 | Java 25+ | [Baixar JAR](artifacts/26.1/simplebackups-fabric-26.1.5.jar)<br>[`fabric/26.1`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/26.1) |
| **1.21.11**<br>SimpleBackups Fabric 21.11.6 | Fabric Loader ≥ 0.19.3<br>Fabric API ≥ 0.141.6+1.21.11<br>Forge Config API Port ≥ 21.11.1 | Java 21+ | [Baixar JAR](artifacts/1.21.11/simplebackups-fabric-21.11.6.jar)<br>[`fabric/1.21.x`](https://github.com/GaBoron/SimpleBackups-Fabric/tree/fabric/1.21.x) |

Cada branch de código-fonte parte do branch upstream correspondente do
SimpleBackups e pode ser compilado de forma independente. Este branch padrão
contém apenas os builds publicados e informações gerais do projeto.

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
