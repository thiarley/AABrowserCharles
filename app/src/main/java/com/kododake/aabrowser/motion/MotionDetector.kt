package com.kododake.aabrowser.motion

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat

class MotionDetector(
    private val context: Context,
    private val onMotionStateChanged: (Boolean) -> Unit
) {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    private var isRegistered = false
    private var isInMotion = false

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val speedKmH = if (location.hasSpeed()) location.speed * 3.6f else 0f
            val nowInMotion = speedKmH >= 5.0f // Movimento considerado a partir de 5 km/h

            if (nowInMotion != isInMotion) {
                isInMotion = nowInMotion
                onMotionStateChanged(isInMotion)
            }
        }

        @Deprecated("Deprecated in API 29")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun start() {
        if (isRegistered) return
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) return

        try {
            if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 2f, locationListener)
                isRegistered = true
            } else if (locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 2f, locationListener)
                isRegistered = true
            }
        } catch (_: SecurityException) {
            // Permissão negada ou restrição de sistema
        }
    }

    fun stop() {
        if (!isRegistered) return
        try {
            locationManager?.removeUpdates(locationListener)
        } catch (_: SecurityException) {}
        isRegistered = false
    }

    fun isCurrentlyInMotion(): Boolean = isInMotion
}
