# <img src="app/src/main/res/drawable/ic_launcher_foreground.png" width="48" height="48" valign="bottom" /> AABrowserCharlesJose v2.3

[![Language English](https://img.shields.io/badge/Language-English-blue?style=for-the-badge)](README.md)
[![Android](https://img.shields.io/badge/Android-15%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)

> [!NOTE]
> **Aviso de Fork & Atribuição de Direitos Autorais**
> 
> **AABrowserCharlesJose** é um fork aprimorado do projeto open-source **[AABrowser](https://github.com/kododake/AABrowser)** criado por **Kododake**. Todos os créditos do projeto base pertencem ao autor original. Este fork adiciona telemetria veicular universal (EV e Combustão HUD), bloqueio de privacidade por PIN de 4 dígitos, compatibilidade com plataformas de streaming (Netflix, Disney+, Prime Video), tela dividida estilo YouTube Music, controle de vídeo em movimento e 100% de privacidade (zero trackers).

---

## 📖 Idiomas Disponíveis / Available Languages

* 🇧🇷 **Português (pt-BR):** Você está lendo a documentação em português.
* 🇺🇸 **English:** [Click here to read the English version](README.md).

---

## 🌟 Lista Completa de Funcionalidades

### 🚗 Funcionalidades Automotivas & Condução
* ⚡ **Painel Universal de Telemetria Automotiva (*EV & Combustion HUD*):**
  * **Modo Elétrico (EV):** Exibe Nível de Bateria ($\%$), Autonomia Restante ($\text{km}$), Potência Instantânea ($\text{kW}$ aceleração/regeneração), Velocidade ($\text{km/h}$) e Temperatura Externa ($\text{°C}$).
  * **Modo Combustão:** Exibe Nível de Combustível ($\%$), Autonomia ($\text{km}$), Consumo Médio ($\text{km/L}$), Velocidade ($\text{km/h}$) e Temperatura Externa ($\text{°C}$).
  * **Modo Detecção Automática (*Auto-Detect*):** Identifica o tipo de propulsão do veículo automaticamente.
  * **Barra de Progresso Dinâmica (`evGaugeBar`):** Indicador em arco com transição automática de cores (🟢 Verde $>50\%$, 🟡 Amarelo $20-50\%$, 🔴 Vermelho $<20\%$).
  * **Posicionamento Flutuante Personalizado:** Mova o painel flutuante para o Topo Direita, Topo Esquerda, Roda-pé Direita ou Roda-pé Esquerda.
* 🎬 **Compatibilidade com Streaming & Persistência SSL (Netflix, Disney+, Prime Video):**
  * **Modo Desktop Automático:** Aplica o User Agent Desktop em sites de streaming para liberar os Web Players oficiais sem telas de bloqueio mobile.
  * **Propagação e Persistência de SSL:** Aprovação automática e salvamento permanente de certificados SSL para domínios e CDNs parceiras (`*.nflxext.com`, `*.disney-plus.net`, etc.).
* 🚗 **Controle de Vídeo em Movimento (4 Modos):**
  * 1) Continuar reproduzindo normalmente
  * 2) Parar vídeo (mantendo o aplicativo aberto)
  * 3) Mini-Player flutuante (Picture-in-Picture no canto da tela)
  * 4) Minimizar app e manter apenas o áudio em segundo plano (`ForegroundService`)
* 🗺️ **Tela Dividida Dupla (*Split Screen Mapa + Navegador*):**
  * Visualização lado a lado do Google Maps e do Navegador/YouTube no estilo YouTube Music.
  * Botão flutuante para alternar os lados com um toque (*Mapa na Esquerda | Navegador na Direita* ou vice-versa).

### 🔐 Funcionalidades de Criptografia & Privacidade
* 🔐 **Bloqueio por PIN de Segurança (*App Lock*):**
  * Tela de bloqueio por PIN de 4 dígitos com criptografia SHA-256 com salt.
  * Oculta todas as abas e o conteúdo do navegador ao entregar o carro em lava-jato, valet ou manutenção.
* 🛡️ **100% Privado (Sem Rastreadores / Telemetria):**
  * Módulo `UmamiTracker` totalmente removido do código-fonte. Zero estatísticas ou métricas enviadas para a internet.

### 🌐 Recursos Principais do Navegador
* 🗂️ **Gerenciador de Abas & Restauração de Sessão:** Abra múltiplas abas, troque/feche abas facilmente e restao de sessão ao reiniciar o app.
* 🎨 **Tema Claro + AMOLED Escuro + Páginas Escuras:** Tema claro, tema escuro AMOLED (preto puro) e escurecimento forçado de páginas web.
* 🏠 **Página Inicial Personalizada & Atalhos Rápidos:** Atalhos visuais Material 3 para YouTube, Netflix, Disney+, Prime Video, Spotify e Google Maps.
* 🧭 **Barra de Endereço Persistente & Botão Flutuante (FAB):** Barra de URL compacta superior e botão flutuante com posição e ação personalizáveis.
* 🔎 **Escala Global de Exibição:** Ajuste o tamanho da interface e do conteúdo web para centrais multimídia panorâmicas (16:9, 21:9, 32:9).

### 📱 Otimizações para Android Auto & Hardware
* 🚗 **Suporte Nativo ao Android Auto Coolwalk 10.0+:** Janelas redimensionáveis (`resizeableActivity`), Picture-in-Picture (`supportsPictureInPicture`) e reagrupamento dinâmico.
* 📱 **Pronto para Android 15 & Android 16 (API 35/37):** Conformidade total com as APIs modernas do Android.
* 📐 **Tratamento de Cantos e Bordas de Tela (`WindowInsetsCompat`):** Impede que botões e menus fiquem escondidos atrás das barras de sistema ou recortes da multimídia.
* 🕹️ **Suporte a Controles Giratórios (*Rotary Controllers / iDrive*):** Foco nativo nos botões para carros com botões físicos no console (BMW, Audi, Mercedes, Mazda).

---

## 📥 Instruções de Instalação no Android 15 e Android 16

### Passo 1: Instalar o APK no Smartphone
1. Baixe o arquivo [AABrowserCharlesJose-2.3.apk](app/build/renamedApks/release/AABrowserCharlesJose-2.3.apk) do repositório ou da aba Releases no GitHub.
2. No seu smartphone com Android 15 ou 16, abra o Gerenciador de Arquivos e toque no `.apk` baixado.
3. Se solicitado, permita a opção **"Instalar apps desconhecidos"** para o seu gerenciador.
4. Toque em **Instalar** para concluir.

### Passo 2: Ativar Fontes Desconhecidas no Android Auto (Apenas na Primeira Vez)
Para que o aplicativo apareça na lista de apps da central multimídia do carro:
1. No smartphone, acesse **Configurações > Apps > Android Auto** (ou abra as configurações do Android Auto).
2. Role até o final da tela e toque na opção **Versão** 10 vezes seguidas até aparecer a mensagem *"Modo do desenvolvedor ativado"*.
3. Toque no **Menu de Três Pontos** (canto superior direito) e selecione **Configurações do desenvolvedor**.
4. Marque a opção **Fontes desconhecidas**.
5. Toque em **Modo do aplicativo** e selecione **Desenvolvedor**.
6. Volte à tela anterior, acesse **Personalizar menu de apps** e garanta que o **AABrowserCharlesJose** está marcado.

### Passo 3: Conceder Permissões no Primeiro Acesso
Ao abrir o aplicativo pela primeira vez, conceda as permissões necessárias:
* 📍 **Permissão de Localização:** Necessária para leitura de velocidade GPS e telemetria do veículo.
* 🎙️ **Permissão de Microfone:** Necessária para busca por voz no navegador.
* 🔔 **Permissão de Notificação (Android 13+):** Necessária para exibir os controles de áudio em segundo plano na barra de notificações.

---

## 🛠️ Como Compilar o Código-Fonte

### Requisitos
* Android SDK (API 35/37)
* JDK 21

```bash
# Compilar APK Release Otimizado
./gradlew assembleRelease

# Compilar APK Debug
./gradlew assembleDebug
```
Localização dos APKs gerados:
* **Release APK:** `app/build/renamedApks/release/AABrowserCharlesJose-2.3.apk`
* **Debug APK:** `app/build/renamedApks/debug/AABrowserCharlesJose-2.3_debug.apk`

---

## 📜 Licença e Créditos

Distribuído sob a licença **[GPLv3](LICENSE)**.
* **Projeto Original:** [Kododake (AABrowser)](https://github.com/kododake/AABrowser)
* **Fork & Melhorias v2.3:** Charles & Thiarley
