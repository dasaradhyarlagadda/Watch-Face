package com.example.watchface

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneListenerService : WearableListenerService() {

    override fun onCreate() {
        super.onCreate()

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE)
                    as NotificationManager

        val channel = NotificationChannel(
            "watch_battery",
            "Watch Battery Alerts",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

       /* if (messageEvent.path == "/test") {

            val battery =
                String(messageEvent.data)

            Log.d(
                "WATCH_BATTERY",
                battery
            )
        }*/
        val battery =
            String(messageEvent.data)

        Prefs.saveBattery(
            this,
            battery
        )

        showNotification(battery)
    }

    private fun showNotification(battery: String) {

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE)
                    as NotificationManager

        val notification =
            NotificationCompat.Builder(
                this,
                "watch_battery"
            )
                .setContentTitle("Watch Battery Low")
                .setContentText("Battery: $battery%")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .build()

        notificationManager.notify(
            1001,
            notification
        )
    }

    object Prefs {

        private const val PREF_NAME = "watch_prefs"
        private const val KEY_BATTERY = "battery"
        private const val KEY_TIME = "time"

        fun saveBattery(context: Context, battery: String) {

            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .edit()
                .putString(KEY_BATTERY, battery)
                .putLong(KEY_TIME, System.currentTimeMillis())
                .apply()
        }

        fun getBattery(context: Context): String {
            return context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(KEY_BATTERY, "--")
                ?: "--"
        }

        fun getTime(context: Context): Long {
            return context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getLong(KEY_TIME, 0)
        }
    }
}