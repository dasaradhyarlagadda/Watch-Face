package com.example.watchface

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.gms.wearable.Wearable
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var batteryText: TextView
    private lateinit var timeText: TextView
    private lateinit var connectedText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        batteryText = TextView(this)
        timeText = TextView(this)
        connectedText = TextView(this)
        layout.addView(connectedText)

        layout.addView(batteryText)
        layout.addView(timeText)

        setContentView(layout)
    }

    override fun onResume() {
        super.onResume()

        Wearable.getNodeClient(this)
            .connectedNodes
            .addOnSuccessListener {

                connectedText.text =
                    if (it.isNotEmpty())
                        "Connected: Yes"
                    else
                        "Connected: No"
            }
        val battery =
            PhoneListenerService.Prefs.getBattery(this)

        val time =
            PhoneListenerService.Prefs.getTime(this)

        batteryText.text =
            "Battery: $battery%"

        if (time > 0) {

            timeText.text =
                "Last Updated: " +
                        SimpleDateFormat(
                            "HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date(time))
        }
    }
}