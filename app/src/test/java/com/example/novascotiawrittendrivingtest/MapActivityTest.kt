package com.example.novascotiawrittendrivingtest

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

@RunWith(RobolectricTestRunner::class)
class MapActivityTest {

    private lateinit var mapActivity: MapActivity

    @Before
    fun setUp() {
        mapActivity = MapActivity()
    }

    @Test
    fun createLocationRequest_createsCorrectly() {
        mapActivity.createLocationRequest()

        val locationRequest = mapActivity.locationRequest
        assertNotNull(locationRequest)
        assertEquals(10000L, locationRequest.interval)
        assertEquals(Priority.PRIORITY_HIGH_ACCURACY, locationRequest.priority)
        assertEquals(5f, locationRequest.smallestDisplacement)
    }
}