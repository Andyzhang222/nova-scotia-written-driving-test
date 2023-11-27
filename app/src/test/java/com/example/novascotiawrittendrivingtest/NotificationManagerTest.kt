package com.example.novascotiawrittendrivingtest;

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.novascotiawrittendrivingtest.notification.NotificationManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotificationManagerTest {

    @Test
    fun testNotificationInterval() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val notificationManager = NotificationManager(context)

        notificationManager.scheduleNotification()

        val expectedIntervalMillis: Long = 24 * 60 * 60 * 1000L

        val actualIntervalMillis: Long = notificationManager.intervalMillis.toLong()

        assertEquals(expectedIntervalMillis, actualIntervalMillis)
    }
}
