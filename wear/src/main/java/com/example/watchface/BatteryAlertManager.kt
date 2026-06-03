package com.example.watchface

import android.content.Context

object BatteryAlertManager {

    private const val PREF_NAME = "battery_alert"
    private const val KEY_ALERT_SENT = "alert_sent"

    fun checkBattery(
        context: Context,
        battery: Int
    ) {

        val prefs =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val alertSent =
            prefs.getBoolean(
                KEY_ALERT_SENT,
                false
            )

        if (battery <= 20 && !alertSent) {

            MessageSender.sendBattery(
                context,
                battery
            )

            prefs.edit()
                .putBoolean(
                    KEY_ALERT_SENT,
                    true
                )
                .apply()
        }

        if (battery >= 25) {

            prefs.edit()
                .putBoolean(
                    KEY_ALERT_SENT,
                    false
                )
                .apply()
        }
    }
}