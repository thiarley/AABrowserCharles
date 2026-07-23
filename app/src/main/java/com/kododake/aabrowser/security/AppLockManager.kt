package com.kododake.aabrowser.security

import android.content.Context
import com.kododake.aabrowser.data.BrowserPreferences

class AppLockManager(private val context: Context) {

    private var isCurrentlyLocked: Boolean = BrowserPreferences.isAppLockEnabled(context)

    fun isLocked(): Boolean {
        if (!BrowserPreferences.isAppLockEnabled(context)) {
            isCurrentlyLocked = false
            return false
        }
        return isCurrentlyLocked
    }

    fun lock() {
        if (BrowserPreferences.isAppLockEnabled(context)) {
            isCurrentlyLocked = true
        }
    }

    fun unlock() {
        isCurrentlyLocked = false
    }

    fun verifyAndUnlock(pin: String): Boolean {
        if (BrowserPreferences.verifyPin(context, pin)) {
            isCurrentlyLocked = false
            return true
        }
        return false
    }
}
