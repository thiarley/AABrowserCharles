package com.kododake.aabrowser.media

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.kododake.aabrowser.MainActivity
import com.kododake.aabrowser.data.BrowserPreferences

class AAMediaBrowserService : MediaBrowserServiceCompat() {

    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        mediaSession = MediaSessionCompat(this, "AAMediaBrowserService").apply {
            setSessionActivity(pendingIntent)
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
                    openMainActivityWithUrl(mediaId)
                }

                override fun onPlay() {
                    openMainActivityWithUrl(null)
                }

                override fun onCustomAction(action: String?, extras: Bundle?) {
                    openMainActivityWithUrl(null)
                }
            })
            val state = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                    PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                )
                .setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1.0f)
                .build()
            setPlaybackState(state)
            isActive = true
        }
        sessionToken = mediaSession?.sessionToken
    }

    private fun openMainActivityWithUrl(url: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            if (!url.isNullOrBlank() && (url.startsWith("http://") || url.startsWith("https://"))) {
                data = Uri.parse(url)
            }
        }
        startActivity(intent)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        val items = mutableListOf<MediaBrowserCompat.MediaItem>()

        // 1. Root / Open Browser Item
        val mainDesc = MediaDescriptionCompat.Builder()
            .setMediaId("https://www.google.com")
            .setTitle("Abrir Navegador Web")
            .setSubtitle("Google")
            .build()
        items.add(MediaBrowserCompat.MediaItem(mainDesc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE))

        // 2. Bookmarks / Saved Sites
        val bookmarks = BrowserPreferences.getBookmarks(applicationContext)
        for (url in bookmarks) {
            val title = getTitleForUrl(url)
            val desc = MediaDescriptionCompat.Builder()
                .setMediaId(url)
                .setTitle(title)
                .setSubtitle(url)
                .build()
            items.add(MediaBrowserCompat.MediaItem(desc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE))
        }

        result.sendResult(items)
    }

    private fun getTitleForUrl(url: String): String {
        val host = runCatching { java.net.URI(url).host?.lowercase() }.getOrNull().orEmpty()
        val cleaned = host.removePrefix("www.").removePrefix("m.").removePrefix("mobile.")
        return when {
            cleaned.contains("google") -> "Google"
            cleaned.contains("youtube") -> "YouTube"
            cleaned.contains("netflix") -> "Netflix"
            cleaned.contains("disney") -> "Disney+"
            cleaned.contains("primevideo") || cleaned.contains("amazon") -> "Prime Video"
            cleaned.contains("hbomax") || cleaned.contains("max.com") -> "HBO Max"
            cleaned.contains("crunchyroll") -> "Crunchyroll"
            cleaned.contains("apple") -> "Apple TV+"
            cleaned.contains("paramount") -> "Paramount+"
            cleaned.isNotBlank() -> cleaned.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            else -> url
        }
    }

    override fun onDestroy() {
        mediaSession?.release()
        super.onDestroy()
    }
}

