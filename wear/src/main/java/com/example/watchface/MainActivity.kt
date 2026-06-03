package com.example.watchface

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var speedManager: SpeedManager
    private var currentSpeed by mutableStateOf("0")
    private var currentTime by mutableStateOf(
        SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date())
    )

    private var batteryPercent by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            100
        )
        val battery = BatteryHelper.getBatteryPercentage(this@MainActivity)
        registerReceiver(
            BatteryReceiver(),
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        if (battery <= 100) {
            batteryPercent = battery
            MessageSender.send(this@MainActivity)
        }
        speedManager = SpeedManager(this) { speed ->

            runOnUiThread {

                currentSpeed = speed
            }
        }
        speedManager.start()
        startClock()
        setContent {

            DashboardScreen(
                time = currentTime,
                speed = currentSpeed,
                battery = batteryPercent
            )
        }
    }

    private fun startClock() {
        Thread {
            while (true) {
                currentTime =
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(Date())

                Thread.sleep(1000)
            }

        }.start()
    }
}

@Composable
fun DashboardScreen(
    time: String,
    speed: String,
    battery: Int
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = time,
                color = Color.White,
                fontSize = 28.sp
            )

            Text(
                text = "$speed km/h",
                color = Color.White,
                fontSize = 36.sp
            )

            Text(
                text = "Battery: $battery%",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ClockScreen() {

    var currentTime by remember {
        mutableStateOf(
            SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date())
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date())

            delay(1000)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 24.sp
        )
    }
}