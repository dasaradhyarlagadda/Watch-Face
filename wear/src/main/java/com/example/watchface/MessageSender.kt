package com.example.watchface

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable

object MessageSender {

    fun send(context: Context) {

        Wearable.getNodeClient(context)
            .connectedNodes
            .addOnSuccessListener { nodes ->

                for (node in nodes) {
                    Log.d("WATCH_SEND", "Found nodes: ${nodes.size}")
                    val battery = BatteryHelper.getBatteryPercentage(context)


                    Wearable.getMessageClient(context)
                        .sendMessage(
                            node.id,
                            "/test",
                            battery.toString().toByteArray()
                        )
                        .addOnSuccessListener {
                            Log.d("WATCH_SEND", "Message sent")
                        }
                        .addOnFailureListener {
                            Log.e("WATCH_SEND", "Failed", it)
                        }
                }
            }
    }

    fun sendBattery(
        context: Context,
        battery: Int
    ) {

        Wearable.getNodeClient(context)
            .connectedNodes
            .addOnSuccessListener { nodes ->

                for (node in nodes) {

                    Wearable.getMessageClient(context)
                        .sendMessage(
                            node.id,
                            "/low_battery",
                            battery.toString().toByteArray()
                        )
                }
            }
    }

}