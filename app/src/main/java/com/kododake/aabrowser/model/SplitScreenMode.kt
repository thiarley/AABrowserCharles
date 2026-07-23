package com.kododake.aabrowser.model

enum class SplitScreenMode(val storageKey: String) {
    DISABLED("disabled"),
    MAP_LEFT_BROWSER_RIGHT("map_left"),
    BROWSER_LEFT_MAP_RIGHT("browser_left");

    companion object {
        fun fromKey(key: String?): SplitScreenMode {
            return entries.firstOrNull { it.storageKey == key } ?: DISABLED
        }
    }
}
