package com.afauzi.userlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.afauzi.userlocation.databinding.ActivityStreetViewBinding
import com.google.android.gms.maps.*

class StreetView : AppCompatActivity(), OnStreetViewPanoramaReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStreetViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStreetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuration streetview in fragment
        val streetViewPanoramaFragment = supportFragmentManager.findFragmentById(R.id.streetViewPanorama) as SupportStreetViewPanoramaFragment
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this)

    }

    // Handle the object streetview
    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
        val sanFracisco = LatLng(37.754130, -122.447129)
        streetViewPanorama.setPosition(sanFracisco)
    }
}