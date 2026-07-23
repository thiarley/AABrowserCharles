package com.kododake.aabrowser.model

enum class InMotionVideoMode(val storageKey: String) {
    CONTINUE("continue"),
    PAUSE("pause"),
    FLOATING_PIP("floating_pip"),
    AUDIO_ONLY("audio_only");

    companion object {
        fun fromKey(key: String?): InMotionVideoMode {
            return entries.firstOrNull { it.storageKey == key } ?: CONTINUE
        }
    }
}
