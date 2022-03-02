package com.afauzi.userlocation

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.afauzi.userlocation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToGeo.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        // Check Location Permission
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
                permission ->
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted
                    Toast.makeText(this, "Precise location access granted", Toast.LENGTH_SHORT).show()
                }
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted
                    Toast.makeText(this, "Only approximate location access granted", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // No Location access granted
                    Toast.makeText(this, "Lokasi tidak diizinkan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }
}