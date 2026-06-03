package com.example.watchface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val battery = BatteryHelper.getBatteryPercentage(this@MainActivity)

        if (battery <= 100) {
            MessageSender.send(this@MainActivity)
        }
        setContent {
//            ClockScreen()
            Button(
                onClick = {
                    MessageSender.send(this@MainActivity)
                }
            ) {
                Text("Send")
            }
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