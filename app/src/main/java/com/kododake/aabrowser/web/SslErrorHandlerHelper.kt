package com.kododake.aabrowser.web

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.net.http.SslError
import android.webkit.SslErrorHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kododake.aabrowser.R
import com.kododake.aabrowser.data.BrowserPreferences

object SslErrorHandlerHelper {

    private val allowedSslHosts = HashSet<String>()

    // Mapeamento de famílias de domínios conhecidas de streaming para propagação automática de SSL
    private val STREAMING_DOMAINS_MAP = mapOf(
        "netflix.com" to listOf("nflxext.com", "nflxso.net", "nflxvideo.net", "nflximg.net", "netflix.net"),
        "disneyplus.com" to listOf("disney-plus.net", "disney.com", "dssott.com", "bamgrid.com"),
        "primevideo.com" to listOf("amazon.com", "pv-cdn.net", "media-amazon.com", "aiv-cdn.net")
    )

    fun handleSslError(activity: Activity, handler: SslErrorHandler, error: SslError) {
        val url = error.url ?: ""
        val host = runCatching { Uri.parse(url).host?.lowercase() }.getOrNull()

        if (host != null && isHostAllowed(activity, host)) {
            handler.proceed()
            return
        }

        if (activity.isFinishing || activity.isDestroyed) {
            handler.cancel()
            return
        }

        val primaryError = error.primaryError
        val errorDescription = getSslErrorDescription(activity, primaryError)
        val hostLabel = host ?: url

        val view = activity.layoutInflater.inflate(R.layout.dialog_cleartext_confirmation, null)
        val titleView = view.findViewById<android.widget.TextView>(R.id.cleartext_title)
        val messageView = view.findViewById<android.widget.TextView>(R.id.cleartext_message)
        val hostContainer = view.findViewById<android.view.View>(R.id.cleartext_host_container)
        val hostLabelView = view.findViewById<android.widget.TextView>(R.id.cleartext_host_label)
        val hostValueView = view.findViewById<android.widget.TextView>(R.id.cleartext_host_value)
        val detailView = view.findViewById<android.widget.TextView>(R.id.cleartext_detail)
        val cancelButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_cancel_dialog)
        val allowOnceButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_allow_once)
        val allowHostButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_allow_host)

        val dialog = MaterialAlertDialogBuilder(activity, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog)
            .setView(view)
            .setCancelable(false)
            .create()

        titleView.text = activity.getString(R.string.ssl_error_title)
        messageView.text = activity.getString(R.string.ssl_error_message, hostLabel)

        hostContainer.visibility = android.view.View.VISIBLE
        hostLabelView.text = activity.getString(R.string.location_access_host_label)
        hostValueView.text = hostLabel

        detailView.visibility = android.view.View.VISIBLE
        detailView.text = activity.getString(R.string.ssl_error_reason_prefix) + " " + errorDescription

        cancelButton.text = activity.getString(R.string.ssl_error_cancel)
        allowOnceButton.text = activity.getString(R.string.ssl_error_proceed)
        allowHostButton.visibility = android.view.View.GONE

        cancelButton.setOnClickListener {
            try { dialog.dismiss() } catch (_: Exception) {}
            handler.cancel()
        }

        allowOnceButton.setOnClickListener {
            try { dialog.dismiss() } catch (_: Exception) {}
            if (host != null) {
                allowedSslHosts.add(host)
                BrowserPreferences.addPersistentSslHost(activity, host)
            }
            handler.proceed()
        }

        try {
            dialog.show()
            val width = (activity.resources.displayMetrics.widthPixels * 0.9).toInt()
            dialog.window?.setLayout(width, android.view.WindowManager.LayoutParams.WRAP_CONTENT)
        } catch (_: Exception) {
            handler.cancel()
        }
    }

    private fun isHostAllowed(context: Context, host: String): Boolean {
        val persistent = BrowserPreferences.getPersistentSslHosts(context)
        val allAllowed = allowedSslHosts + persistent

        for (allowed in allAllowed) {
            if (host == allowed || host.endsWith(".$allowed")) {
                return true
            }
            // Verificar família de streaming
            for ((mainDomain, cdnDomains) in STREAMING_DOMAINS_MAP) {
                val family = listOf(mainDomain) + cdnDomains
                val allowedInFamily = family.any { allowed == it || allowed.endsWith(".$it") }
                val targetInFamily = family.any { host == it || host.endsWith(".$it") }
                if (allowedInFamily && targetInFamily) {
                    return true
                }
            }
        }
        return false
    }

    private fun getSslErrorDescription(activity: Activity, errorCode: Int): String {
        return when (errorCode) {
            SslError.SSL_EXPIRED -> activity.getString(R.string.ssl_error_expired)
            SslError.SSL_IDMISMATCH -> activity.getString(R.string.ssl_error_idmismatch)
            SslError.SSL_UNTRUSTED -> activity.getString(R.string.ssl_error_untrusted)
            SslError.SSL_NOTYETVALID -> activity.getString(R.string.ssl_error_notyetvalid)
            SslError.SSL_DATE_INVALID -> activity.getString(R.string.ssl_error_date_invalid)
            else -> activity.getString(R.string.ssl_error_invalid)
        }
    }

    fun clearAllowedSslHosts(context: Context) {
        allowedSslHosts.clear()
        BrowserPreferences.clearPersistentSslHosts(context)
    }
}
