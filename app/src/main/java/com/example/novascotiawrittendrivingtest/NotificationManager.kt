package com.example.novascotiawrittendrivingtest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class NotificationManager(private val context: Context) {

    fun scheduleNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        // Set the notification time frame
        // val intervalMillis = 24 * 60 * 60 * 1000 // every day
        val intervalMillis = 60_000 // temporarily set to every minute
        val triggerAtMillis = System.currentTimeMillis() + intervalMillis

        // Set a repeating alarm that triggers a notification
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis.toLong(),
            pendingIntent
        )
    }
}
