package com.example.watchface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (intent.action != Intent.ACTION_BATTERY_CHANGED) {
            return
        }

        val level = intent.getIntExtra(
            BatteryManager.EXTRA_LEVEL,
            -1
        )

        val scale = intent.getIntExtra(
            BatteryManager.EXTRA_SCALE,
            -1
        )

        val battery =
            (level * 100) / scale

        BatteryAlertManager.checkBattery(
            context,
            battery
        )
    }
}