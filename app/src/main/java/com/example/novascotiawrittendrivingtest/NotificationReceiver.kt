package com.example.novascotiawrittendrivingtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/*
    Class that extends BroadcastReceiver to handle broadcast messages
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiver", "Received notification broadcast")

        val channelId = "default_channel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_practice_test)
            .setContentTitle("Reminder")
            .setContentText("Don't forget to practice today!") // notification text
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)

        val notificationId = 1 // Unique ID for the notification
        val notificationTag = "unique_tag" // Unique tag for the notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()

        // Check for permission before sending notifications
        if (notificationManager.areNotificationsEnabled()) {
            try {
                notificationManager.notify(notificationTag, notificationId, notification)
            } catch (e: SecurityException) {
                // Handle the SecurityException
                e.printStackTrace()
                // Notify the user or handle the exception as needed
            }
        } else {
            // Notifications are disabled, notify the user or handle accordingly
        }
    }
}
