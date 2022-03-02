package com.afauzi.userlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.afauzi.userlocation.databinding.ActivityCurrentPlaceBinding
import com.google.android.gms.maps.*

class CurrentPlace : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCurrentPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurrentPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Mendapatkan handle untuk fragment dan mendafatarkan callback
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Mendapatkan handle untuk object google map
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(MarkerOptions().position(LatLng(-6.184589743105369, 106.82654263439325)).title("Jakarta"))
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

}