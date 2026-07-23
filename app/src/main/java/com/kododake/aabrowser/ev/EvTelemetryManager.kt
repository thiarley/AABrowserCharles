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
    val isConnectedToVehicle: Boolean = false,
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
    private var hasRealCarData = false

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
            else -> VehicleEngineType.EV
        }

        // If we haven't received real car CAN data yet, fallback to calculated/preference state
        if (!hasRealCarData) {
            currentData = if (selectedType == VehicleEngineType.COMBUSTION) {
                currentData.copy(
                    engineType = VehicleEngineType.COMBUSTION,
                    fuelOrBatteryPercent = currentData.fuelOrBatteryPercent.takeIf { it != 78 } ?: 85,
                    rangeKm = currentData.rangeKm.takeIf { it != 320 } ?: 540,
                    powerOrConsumption = 12.8f
                )
            } else {
                currentData.copy(
                    engineType = VehicleEngineType.EV,
                    powerOrConsumption = if (currentData.speedKmH > 5f) (currentData.speedKmH * 0.25f) else 0f
                )
            }
        }
    }

    private val logEntries = java.util.Collections.synchronizedList(mutableListOf<String>())

    private fun addLog(msg: String) {
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.US).format(java.util.Date())
        val entry = "[$timestamp] $msg"
        logEntries.add(entry)
        if (logEntries.size > 50) logEntries.removeAt(0)
    }

    fun getDiagnosticLogs(): String {
        if (logEntries.isEmpty()) return "Nenhum evento registrado ainda.\nGaranta que a localização/GPS esteja ativada no celular/multimídia."
        return logEntries.joinToString("\n")
    }

    @SuppressLint("MissingPermission")
    fun start() {
        if (isRunning) return
        isRunning = true
        addLog("Iniciando telemetria...")
        val gpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        val netEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
        addLog("GPS Provider ativo: $gpsEnabled, Network Provider ativo: $netEnabled")

        currentData = currentData.copy(isConnectedToVehicle = true)

        tryInitCarHardware()

        runCatching {
            if (gpsEnabled) {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1f, this)
                addLog("Registrado listener de localização em GPS_PROVIDER")
            }
            if (netEnabled) {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 1f, this)
                addLog("Registrado listener de localização em NETWORK_PROVIDER")
            }
        }.onFailure { e ->
            addLog("Erro ao registrar localização: ${e.message}")
        }
        handler.post(updateRunnable)
    }

    private fun tryInitCarHardware() {
        runCatching {
            val carClass = Class.forName("android.car.Car")
            val createCarMethod = carClass.getMethod("createCar", Context::class.java)
            val carObj = createCarMethod.invoke(null, context)
            val getCarManagerMethod = carClass.getMethod("getCarManager", String::class.java)
            val carPropertyManager = getCarManagerMethod.invoke(carObj, "property")
            if (carPropertyManager != null) {
                addLog("CarPropertyManager inicializado com sucesso no sistema nativo do veículo.")
                hasRealCarData = true
            }
        }.onFailure {
            addLog("CarPropertyManager nativo não disponível neste dispositivo/host. Usando provedor GPS + Telemetria de localização.")
        }
    }

    fun stop() {
        if (!isRunning) return
        isRunning = false
        addLog("Parando telemetria.")
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
        addLog("Localização recebida: lat=${location.latitude}, speed=${speedKmH.toInt()}km/h, accuracy=${location.accuracy}m, prov=${location.provider}")
        currentData = currentData.copy(
            speedKmH = speedKmH,
            isConnectedToVehicle = true
        )
        updateTelemetryState()
        onTelemetryUpdated(currentData)
    }

    override fun onProviderEnabled(provider: String) {
        addLog("Provedor de localização ATIVADO: $provider")
    }

    override fun onProviderDisabled(provider: String) {
        addLog("Provedor de localização DESATIVADO: $provider")
    }
}

