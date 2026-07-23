# <img src="app/src/main/res/drawable/ic_launcher_foreground.png" width="48" height="48" valign="bottom" /> AABrowserCharlesJose v2.3

[![Language pt-BR](https://img.shields.io/badge/Language-Portugu%C3%AAs%20(pt--BR)-green?style=for-the-badge)](README.pt-BR.md)
[![Android](https://img.shields.io/badge/Android-15%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)

> [!NOTE]
> **Fork & Attribution Notice**
> 
> **AABrowserCharlesJose** is an enhanced fork of the open-source **[AABrowser](https://github.com/kododake/AABrowser)** project created by **Kododake**. All credits for the original base project belong to the original author. This fork introduces universal vehicle telemetry (EV & Combustion HUD), 4-digit App Lock PIN privacy, streaming compatibility (Netflix, Disney+, Prime Video), YouTube Music-style split-screen layout, motion video controls, and full privacy (zero trackers).

> [!WARNING]
> **Safety & Driver Disclaimer**
> 
> Drivers **MUST NOT** watch videos or look at interactive media while driving. Video playback options in motion (such as Picture-in-Picture or In-Motion Video modes) are **EXCLUSIVELY intended for passengers** to enjoy entertainment safely. The driver must maintain full attention on the road at all times. By default, video playback minimizes and switches to audio-only when the vehicle is in motion.

---

## 📖 Available Languages / Idiomas disponíveis

* 🇺🇸 **English:** You are reading the main documentation.
* 🇧🇷 **Português (pt-BR):** [Clique aqui para ler a versão em Português (pt-BR)](README.pt-BR.md).

---

## 🌟 Comprehensive Features (Complete List)

### 🚗 Vehicle & Driving Features
* 🎬 **Streaming Compatibility & Persistent SSL (Netflix, Disney+, Prime Video):**
  * **Auto-Desktop Mode:** Enforces Desktop User Agent on streaming sites to load official Web Players without mobile block screens.
  * **Subdomain SSL Propagation & Persistence:** Automatic wildcard SSL approval for streaming CDNs (`*.nflxext.com`, `*.disney-plus.net`, etc.) saved permanently in preferences.

### 🔐 Security & Privacy Features
* 🔐 **App Lock PIN Privacy Protection:**
  * 4-digit PIN lock screen with SHA-256 + Salt encryption.
  * Fullscreen lock overlay hides all browser tabs and web content when leaving your car for valet, car wash, or maintenance.
* 🛡️ **100% Private (No Trackers / Telemetry):**
  * `UmamiTracker` module completely deleted from codebase. Zero usage statistics or telemetry sent to the internet.

### 🌐 Browser Core Features
* 🗂️ **Real Tab Manager & Session Restore:** Open multiple tabs, switch between tabs easily, close tabs, and optional tab session restore on app launch.
* 🎨 **Light + AMOLED Dark Mode + Beta Dark Pages:** Bright light theme, true-black AMOLED dark theme, and optional forced dark page rendering.
* 🏠 **Custom Home Page & Speed Dial Cards:** Custom start page with Material 3 elevated shortcut cards for YouTube, Netflix, Disney+, Prime Video, Spotify, and Google Maps.
* 🧭 **Persistent Address Bar & Configurable FAB:** Optional compact top URL bar and customizable floating action button (position and click action).
* 🔎 **Global Display Scale:** Adjust UI and web content scaling with presets or custom percentages for widescreen automotive displays (16:9, 21:9, 32:9).

### 📱 Android Auto & Hardware Optimizations
* 🚗 **Android Auto Coolwalk 10.0+ Native Support:** Window resizing (`resizeableActivity`), Picture-in-Picture (`supportsPictureInPicture`), and dynamic multi-window re-layout.
* 📱 **Android 15 & Android 16 (API 35/37) Ready:** Full compliance with modern Android system APIs.
* 📐 **Edge-to-Edge & System Insets:** `WindowInsetsCompat` handling prevents UI elements from being hidden under rounded display corners or notches.
* 🕹️ **Rotary Controller / iDrive Support:** Focus highlights on interactive controls for cars with console D-Pad / rotary knobs (BMW, Audi, Mercedes, Mazda).

---

## 📥 Installation Instructions for Android 15 & Android 16

### Step 1: Install the APK on your Phone
1. Download [AABrowserCharlesJose-2.3.apk](app/build/renamedApks/release/AABrowserCharlesJose-2.3.apk) from the repository or GitHub Releases.
2. On your Android 15 or 16 phone, open your File Manager and tap the downloaded `.apk` file.
3. If prompted, allow **"Install unknown apps"** for your file manager.
4. Tap **Install** to complete the installation.

### Step 2: Enable Android Auto Unknown Sources (First Time Only)
To allow the app to appear in your car's Android Auto launcher:
1. On your phone, go to **Settings > Apps > Android Auto** (or open Android Auto settings).
2. Scroll down to the bottom and tap **Version** 10 times consecutively until a popup says *"Developer settings enabled"*.
3. Tap the **Three Dots Menu** (top right) and select **Developer settings**.
4. Check **Unknown sources**.
5. Tap **Application Mode** and select **Developer**.
6. Go back to Android Auto settings, select **Customize Launcher**, and ensure **AABrowserCharlesJose** is checked.

### Step 3: Grant Required Permissions on First Launch
When opening the app for the first time, grant the requested runtime permissions:
* 📍 **Location Permission:** Required for GPS real-time speed & vehicle telemetry.
* 🎙️ **Microphone Permission:** Required for voice search and speech-to-text input.
* 🔔 **Notification Permission (Android 13+):** Required for background audio playback service controls.

---

## 🛠️ Building from Source

### Requirements
* Android SDK (API 35/37)
* JDK 21

```bash
# Build Release APK
./gradlew assembleRelease

# Build Debug APK
./gradlew assembleDebug
```
Output APK locations:
* **Release APK:** `app/build/renamedApks/release/AABrowserCharlesJose-2.3.apk`
* **Debug APK:** `app/build/renamedApks/debug/AABrowserCharlesJose-2.3_debug.apk`

---

## 📜 License & Credits

Distributed under the **[GPLv3 License](LICENSE)**.
* **Original Project:** [Kododake (AABrowser)](https://github.com/kododake/AABrowser)
* **Fork & v2.3 Enhancements:** Charles & Thiarley
