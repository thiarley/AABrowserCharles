package com.kododake.aabrowser.ev

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import com.kododake.aabrowser.data.BrowserPreferences

enum class VehicleEngineType {
    EV,
    COMBUSTION
}

data class EvTelemetryData(
    val engineType: VehicleEngineType = VehicleEngineType.EV,
    val fuelOrBatteryPercent: Int = 78,
    val rangeKm: Int = 320,
    val powerOrConsumption: Float = 14.5f,
    val speedKmH: Float = 0f,
    val tempCelsius: Float = 26f,
    val gear: String = "D"
)

class EvTelemetryManager(
    private val context: Context,
    private val onTelemetryUpdated: (EvTelemetryData) -> Unit
) : LocationListener {

    private val locationManager: LocationManager? =
        context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var currentData = EvTelemetryData()

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                updateTelemetryState()
                onTelemetryUpdated(currentData)
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun updateTelemetryState() {
        val prefType = BrowserPreferences.getVehicleType(context)
        val selectedType = when (prefType) {
            "combustion" -> VehicleEngineType.COMBUSTION
            "ev" -> VehicleEngineType.EV
            else -> VehicleEngineType.EV // Default auto detection
        }

        currentData = if (selectedType == VehicleEngineType.COMBUSTION) {
            currentData.copy(
                engineType = VehicleEngineType.COMBUSTION,
                fuelOrBatteryPercent = 85,
                rangeKm = 540,
                powerOrConsumption = 12.8f
            )
        } else {
            currentData.copy(
                engineType = VehicleEngineType.EV,
                fuelOrBatteryPercent = 78,
                rangeKm = 320,
                powerOrConsumption = if (currentData.speedKmH > 5f) (currentData.speedKmH * 0.25f) else 0f
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun start() {
        if (isRunning) return
        isRunning = true
        runCatching {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                1f,
                this
            )
        }
        handler.post(updateRunnable)
    }

    fun stop() {
        if (!isRunning) return
        isRunning = false
        runCatching {
            locationManager?.removeUpdates(this)
        }
        handler.removeCallbacks(updateRunnable)
    }

    override fun onLocationChanged(location: Location) {
        val speedKmH = if (location.hasSpeed()) {
            (location.speed * 3.6f)
        } else {
            0f
        }
        currentData = currentData.copy(speedKmH = speedKmH)
        updateTelemetryState()
        onTelemetryUpdated(currentData)
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
