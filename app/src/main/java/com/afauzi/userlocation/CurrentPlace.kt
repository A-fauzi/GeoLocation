package com.afauzi.userlocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.afauzi.userlocation.databinding.ActivityCurrentPlaceBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.tasks.OnSuccessListener


class CurrentPlace : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private lateinit var binding: ActivityCurrentPlaceBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurrentPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoStreetView.setOnClickListener {

            startActivity(Intent(this, StreetView::class.java))
        }

        // Mendapatkan handle untuk fragment dan mendafatarkan callback
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Mendapatkan handle untuk object google map
    override fun onMapReady(googleMap: GoogleMap) {

        // GET MY CURRENT LOCATION
        val mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocation.lastLocation
            .addOnSuccessListener(this) { loc -> // Do it all with location
                // Sets initials state location
                val location = LatLng(loc!!.latitude, loc.longitude)
                googleMap.addMarker(MarkerOptions().position(location).title("my location"))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 20.0f))
            }
            .addOnFailureListener {
                Toast.makeText(
                    this, "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }


        // Call OnPoiClickListener
        googleMap.setOnPoiClickListener(this)

        // Call status awal peta
        startMap()
    }

    // Memulai status awal peta
    private fun startMap() {
        // Initials GoogleMapsOption()
        val options = GoogleMapOptions()

        // Configuration
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
            .compassEnabled(false)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(true)
    }

    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(
            this, """
            Clicked: ${poi.name}
            PlaceId: ${poi.placeId}
            Latitude: ${poi.latLng.latitude}
            Longtitude: ${poi.latLng.longitude}
        """.trimIndent(), Toast.LENGTH_SHORT
        ).show()
    }

}