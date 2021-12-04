package com.vano.map.google.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.vano.map.google.adapter.MarkerInfoWindowAdapter
import com.vano.map.google.entity.MyPlace
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityGoogleMapBinding
import org.jetbrains.anko.toast
import timber.log.Timber


class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val TITLE = "Google map"
        const val ERROR_1 = "SupportMapFragment was not initialized!"
        const val MY_KEY = "AIzaSyCitkDUemXUkut6_66B3SvDtGfciT3GU9g"
        val NULL_ISLAND = LatLng(0.0, 0.0)
        const val EMPTY_STRING = ""
        const val ZOOM = 8f
    }

    private var binding: ActivityGoogleMapBinding? = null

    private var mapFragment: SupportMapFragment? = null

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var autocompleteFragment: AutocompleteSupportFragment? = null

    private var circle: Circle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        binding = ActivityGoogleMapBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar?.root)
        supportActionBar?.title = TITLE

        checkPermissions()
        Places.initialize(this, MY_KEY)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        addAutocompleteSearch()
        initMap()
        //addMarker()
    }

    private fun addAutocompleteSearch() {
        autocompleteFragment = supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.BUSINESS_STATUS,
                Place.Field.LAT_LNG
            )
        )

        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                addMarker(
                    MyPlace(
                        place.name.toString(),
                        place.latLng ?: NULL_ISLAND,
                        place.businessStatus?.toString() ?: EMPTY_STRING,
                        place.address ?: EMPTY_STRING
                    )
                )
                place.latLng?.let {
                    moveCamera(it)
                }
            }

            override fun onError(status: Status) {
            }
        })
    }

    private fun moveCamera(position: LatLng) {
        mapFragment!!.getMapAsync {
            it.moveCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        position,
                        ZOOM
                    )
                )
            )
        }
    }

    private fun addMarker(
        myPlace: MyPlace = MyPlace(
            "Test", NULL_ISLAND, "About", "Norvezhskaya street,20"
        )
    ) {

        mapFragment?.getMapAsync {
            val marker = it.addMarker(
                MarkerOptions()
                    .title(myPlace.name)
                    .position(myPlace.geometry)
            )

            marker?.tag = myPlace
        }

    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        var cameraPosition: CameraPosition? = null

        mapFragment = SupportMapFragment.newInstance()
        fusedLocationClient?.lastLocation?.addOnSuccessListener {
            if (it != null)
                cameraPosition =
                    CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), ZOOM)
        }

        if (mapFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.google_map_container, mapFragment!!)
                .commit()

            mapFragment!!.getMapAsync {
                it.isMyLocationEnabled = true
                if (cameraPosition != null)
                    it.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition!!))
                it.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
                setMarkerClick(it)
                it.setOnMapClickListener { position ->
                    addMarker(MyPlace(geometry = position))
                }
            }
        } else {
            toast(ERROR_1)
        }
    }

    private fun setMarkerClick(it: GoogleMap) {
        it.setOnMarkerClickListener { marker ->
            circle?.remove()
            val circleOptions = CircleOptions().center(marker.position).radius(100000.0)
                .fillColor(R.color.red)
                .strokeColor(R.color.primary)

            circle = it.addCircle(circleOptions)
            false
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
        }
    }

    override fun onMapReady(map: GoogleMap) {
    }

    override fun onStart() {
        super.onStart()
        mapFragment?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapFragment?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapFragment?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapFragment?.onResume()
    }

}