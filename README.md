# <img src="app/src/main/res/drawable/ic_launcher_foreground.png" width="48" height="48" valign="bottom" /> AABrowserCharlesJose v2.3

[![Android](https://img.shields.io/badge/Android-15%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)

> [!NOTE]
> **Fork & Attribution Notice**
> 
> **AABrowserCharlesJose** é um fork aprimorado do projeto open-source original **[AABrowser](https://github.com/kododake/AABrowser)** desenvolvido por **Kododake**. Todos os créditos do projeto base pertencem ao autor original. Este fork adiciona recursos de segurança, telemetria veicular universal (EV e Combustão), modo split-screen estilo YouTube Music, suporte avançado a streaming e personalizações de interface gráfica.

---

## ✨ Novos Recursos da Versão 2.3 (Release)

### 🚘 1. Painel Universal de Telemetria Automotiva (*Vehicle Telemetry Dashboard HUD*)
* **Suporte Híbrido (EV & Combustão):** Exibe métricas de veículos **Elétricos (EV)** e **A Combustão (Gasolina / Flex / Diesel)** com detecção automática (*Auto-Detect*).
* **Indicadores em Tempo Real:** 
  * 🔋/⛽ Nível de Bateria / Combustível ($\%$) e Autonomia em quilômetros ($\text{km}$).
  * ⚡/⛽ Consumo de Energia Instantâneo ($\text{kW}$) ou Consumo Médio ($\text{km/L}$).
  * 🚗 Velocidade em tempo real ($\text{km/h}$) e Temperatura externa ($\text{°C}$).
* **Barra de Progresso Dinâmica (`evGaugeBar`):** Barra em arco com variação automática de cor (Verde $\rightarrow$ Amarelo $\rightarrow$ Vermelho crítico).
* **Posicionamento Flutuante:** Escolha da posição na tela (*Topo Direita, Topo Esquerda, Roda-pé Direita, Roda-pé Esquerda*).

### 🔐 2. Bloqueio por PIN de Segurança (*App Lock*)
* **Proteção de Privacidade:** Criptografia SHA-256 com salt para proteger suas contas pessoais (Google, bancos, streaming) quando o veículo estiver em lava-jato, valet ou manutenção.
* **Overlay em Tela Cheia:** Oculta todo o conteúdo do navegador e abas até a digitação do PIN correto de 4 dígitos.

### 🎬 3. Compatibilidade Avançada com Streaming (Netflix, Disney+, Prime Video)
* **Auto-Desktop Mode:** Alternância automática para User Agent Desktop ao acessar Netflix, Disney+ e Amazon Prime Video, liberando o Web Player oficial.
* **Propagação & Persistência SSL:** Autorização automática e salvamento permanente de certificados SSL para domínios e CDNs parceiras (`*.nflxext.com`, `*.disney-plus.net`, etc.), eliminando popups repetidos.

### 🚗 4. Controle de Vídeo em Movimento (4 Modos)
* **Continuar reproduzindo normalmente**
* **Parar vídeo (sem fechar o app)**
* **Mini-player flutuante (PiP no canto da tela)**
* **Minimizar app e manter apenas o áudio em segundo plano**

### 🗺️ 5. Layout Tela Dividida (*Split Screen Mapa + Browser*)
* Visualização lado a lado do **Mapa (Google Maps)** e do **Navegador / YouTube** com botão flutuante para inversão de lados (*Mapa na Esquerda* ou *Navegador na Esquerda*).

### 🛡️ 6. 100% Privado (Sem Trackers ou Telemetria)
* Módulo de rastreamento `UmamiTracker` completamente removido do código-fonte. Zero estatísticas enviadas para a internet.

### 📱 7. Otimizado para Android Auto Coolwalk (10.0+) & Android 16 Ready
* Suporte nativo a janela redimensionável (`resizeableActivity`), Picture-in-Picture (`supportsPictureInPicture`), tratamento de recortes de tela (`WindowInsetsCompat`) e navegação por controles giratórios do console (*Rotary Controllers / iDrive*).

---

## 🛠️ Como Compilar o Projeto

### Pré-requisitos
* Android SDK (Plataforma 37 / API 35+)
* Java JDK 21

### Comando de Build
```bash
./gradlew assembleDebug
```
O APK final será gerado em: `app/build/renamedApks/debug/AABrowserCharlesJose-2.3_debug.apk`.

---

## 📜 Licença e Créditos

Este projeto está sob a licença **[GPLv3](LICENSE)**.
* **Autor Original:** [Kododake (AABrowser)](https://github.com/kododake/AABrowser)
* **Fork & Melhorias v2.3:** Charles & Thiarley
