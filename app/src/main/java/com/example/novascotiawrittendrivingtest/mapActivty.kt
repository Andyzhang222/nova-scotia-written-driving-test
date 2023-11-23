package com.example.novascotiawrittendrivingtest

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        createLocationRequest()
        setupLocationCallback()

    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                lastKnownLocation = locationResult.lastLocation
                Log.d("LocationUpdate", "Location updated: $lastKnownLocation") // Debugging log
                updateUIWithLocation(lastKnownLocation)
                findNearestDrivingSchool()
            }
        }
    }   private fun onLocationResult(locationResult: LocationResult) {
        lastKnownLocation = locationResult.lastLocation
        Log.d("LocationUpdate", "Location updated: $lastKnownLocation")
        updateUIWithLocation(lastKnownLocation)
        findNearestDrivingSchool() // This is the correct place to call it
    }
    private fun findNearestDrivingSchool() {
        if (!::mMap.isInitialized) {
            Log.d("DEBUG", "Map is not initialized yet.") // Debugging log
            return // mMap not initialized, return early
        }
        if (lastKnownLocation == null) {
            Log.d("DEBUG", "Last known location is null.") // Debugging log
            return // Location not determined, return early
        }
        if (!::mMap.isInitialized) {
            return // mMap not initialized, return early
        }
        val drivingSchools = listOf(
            // Replace this with actual driving school data
            LatLng(44.6465, -63.5926), // Example location in Nova Scotia
            LatLng(44.6407, -63.5696),
            LatLng(44.6675, -63.5630),
            LatLng(44.6499, -63.6099),
            LatLng(44.6521, -63.6502),
            LatLng(44.6652, -63.6425)
        )

        val nearestSchool = drivingSchools.minByOrNull { schoolLatLng ->
            lastKnownLocation?.let { currentLocation ->
                val results = FloatArray(6)
                Location.distanceBetween(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    schoolLatLng.latitude,
                    schoolLatLng.longitude,
                    results
                )
                results[0]
            } ?: Float.MAX_VALUE
        }
        nearestSchool?.let {
            // Debugging: Log the LatLng of the nearest school
            Log.d("DEBUG", "Nearest school: ${it.latitude}, ${it.longitude}")
            // Update the UI to show the nearest driving school
            mMap.addMarker(MarkerOptions().position(it).title("Nearest Driving School"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
        } ?: Log.d("DEBUG", "No nearest school found")

    }
    private fun updateUIWithLocation(location: Location?) {
        location?.let {
            val currentLatLng = LatLng(it.latitude, it.longitude)
            mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(10000L) // Interval in milliseconds
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(5f) // Your desired minimum displacement between location updates
            .build()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        updateLocationUI()
        lastKnownLocation?.let {
            findNearestDrivingSchool()
        }
    }

    private fun updateLocationUI() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            startLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                updateLocationUI()
            } else {
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}