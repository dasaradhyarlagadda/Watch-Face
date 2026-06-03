package com.example.watchface

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.*

class SpeedManager(
    private val context: Context,
    private val onSpeedChanged: (String) -> Unit
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest =
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000
        )
            .setMinUpdateIntervalMillis(1000)
            .build()

    private val locationCallback =
        object : LocationCallback() {

            override fun onLocationResult(
                result: LocationResult
            ) {
                val location: Location =
                    result.lastLocation ?: return

                val speedKmh =
                    location.speed * 3.6
                Log.d(
                    "SPEED_TEST",
                    "Location received: ${location.latitude}, ${location.longitude}"
                )
                onSpeedChanged(
                    speedKmh.toInt().toString()
                )
            }
        }

    @SuppressLint("MissingPermission")
    fun start() {
        Log.d("SPEED_TEST", "Starting location updates")
        fusedLocationClient
            .requestLocationUpdates(
                locationRequest,
                locationCallback,
                context.mainLooper
            )
            .addOnSuccessListener {
                Log.d("SPEED_TEST", "Location request registered")
            }
            .addOnFailureListener {
                Log.e("SPEED_TEST", "Location request failed", it)
            }
    }

    fun stop() {

        fusedLocationClient.removeLocationUpdates(
            locationCallback
        )
    }
}