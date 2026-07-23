# Changelog

## [2.3] - 2026-07-23

### Changed / Cleaned Up
- Reverted experimental `AAMediaBrowserService` media launcher integration to restore pure Web Browser Activity mode on Android Auto.
- Removed obsolete EV Telemetry module (`EvTelemetryManager`, CAN bus permissions, HUD widget, and associated settings).
- Removed Video In Motion configuration (`InMotionVideoMode` and `MotionDetector`) and its settings UI.
- Cleaned up `AndroidManifest.xml` and `automotive_app_desc.xml` to keep the codebase minimal, clean, and bug-free.
