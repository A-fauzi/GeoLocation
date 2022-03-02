package com.afauzi.userlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.afauzi.userlocation.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    // Add Mapping
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 25F))
//    }


    // Polyline
    override fun onMapReady(googleMap: GoogleMap) {

        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        val polyline1 = googleMap.addPolyline(PolylineOptions()
            .clickable(true)
            .add(
                LatLng(-35.016, 143.321),
                LatLng(-34.747, 145.592),
                LatLng(-34.364, 147.891),
                LatLng(-33.501, 150.217),
                LatLng(-32.306, 149.248),
                LatLng(-32.491, 147.309)))

        // Add polygons to indicate areas on the map.
        val polygon1 = googleMap.addPolygon(PolygonOptions()
            .clickable(true)
            .add(
                LatLng(-27.457, 153.040),
                LatLng(-33.852, 151.211),
                LatLng(-37.813, 144.962),
                LatLng(-34.928, 138.599)))




        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.tag = "A"
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.tag = "alpha"


    }

    // Style in Polyline
    private val COLOR_BLACK_ARGB = -0x1000000
    private val POLYLINE_STROKE_WIDTH_PX = 12

    /**
     * Styles the polyline, based on type.
     * @param polyline The polyline object that needs styling.
     */
    private fun stylePolyLine(polyline: Polyline) {
        // get the data object stored with the polyline
        val type = polyline.tag.toString() ?: ""
        when (type) {
            "A" -> {
                // Use a custom bitmap as the cap at the start of the line
                polyline.startCap = CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_play_arrow_24))
            }
            "B" -> {
                // Use a round cap the start of the line
                polyline.startCap = RoundCap()
            }
        }

        polyline.endCap = RoundCap()
        polyline.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        polyline.color = COLOR_BLACK_ARGB
        polyline.jointType = JointType.ROUND
    }

    // Menanagni click pada polyLine
    private var PATTERN_GAP_LENGTH_PX = 20
    private var DOT: PatternItem = Dot()
    private var GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

    // create a stroke pattern of a gap followed by a dot
    private val PATTERN_POLYLINE_DOTTED = listOf(DOT, GAP)
    /**
     * Listens for clicks on a polyline.
     * @param polyline The polyline object that the user has clicked.
     */
    override fun onPolylineClick(polyline: Polyline) {
        // Flip from solid stroke to dotted stroke pattern
        if (polyline.pattern == null || !polyline.pattern!!.contains(DOT)) {
            polyline.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            // The default pattern is a solid stroke
            polyline.pattern = null
        }
        Toast.makeText(this, "Route type " + polyline.tag.toString(), Toast.LENGTH_SHORT).show()
    }

    // Style the polygon.
    private val COLOR_WHITE_ARGB = -0x1
    private val COLOR_DARK_GREEN_ARGB = -0xc771c4
    private val COLOR_LIGHT_GREEN_ARGB = -0x7e387c
    private val COLOR_DARK_ORANGE_ARGB = -0xa80e9
    private val COLOR_LIGHT_ORANGE_ARGB = -0x657db
    private val POLYGON_STROKE_WIDTH_PX = 8
    private val PATTERN_DASH_LENGTH_PX = 20

    private val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())

    // Create a stroke pattern of a gap followed by a dash.
    private val PATTERN_POLYGON_ALPHA = listOf(GAP, DASH)

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private val PATTERN_POLYGON_BETA = listOf(DOT, GAP, DASH, GAP)

    /**
     * Styles the polygon, based on type.
     * @param polygon The polygon object that needs styling.
     */
    override fun onPolygonClick(polygon: Polygon) {
        // Get the data object stored with the polygon
        val type = polygon.tag.toString() ?: ""
        var pattern: List<PatternItem>? = null
        var strokeCollor = COLOR_BLACK_ARGB
        var fillColor = COLOR_WHITE_ARGB
        when(type) {
            "alpha" -> {
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA
                strokeCollor = COLOR_DARK_GREEN_ARGB
                fillColor = COLOR_LIGHT_GREEN_ARGB
            }
            "beta" -> {
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA
                strokeCollor = COLOR_DARK_ORANGE_ARGB
                fillColor = COLOR_LIGHT_ORANGE_ARGB
            }
        }
        polygon.strokePattern = pattern
        polygon.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygon.strokeColor = strokeCollor
        polygon.fillColor = fillColor
    }


}