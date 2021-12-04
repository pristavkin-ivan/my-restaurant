package com.vano.map.yandex.fragment


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.FragmentYandexMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import org.jetbrains.anko.support.v4.toast
import com.yandex.mapkit.map.GeoObjectSelectionMetadata

import com.yandex.mapkit.layers.GeoObjectTapEvent


class YandexMapFragment : Fragment(), UserLocationObjectListener, CameraListener,
    Session.SearchListener, GeoObjectTapListener, InputListener {

    private var mapView: MapView? = null

    private var location: Location? = null

    private var userLocationLayer: UserLocationLayer? = null

    private var searchManager: SearchManager? = null

    private var binding: FragmentYandexMapBinding? = null

    private var searchSession: Session? = null

    companion object {
        const val MAP_KEY = "8b477d6f-7e31-4fc7-aad0-66dcbb2dd5d1"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(MAP_KEY)
        MapKitFactory.initialize(requireContext())
        SearchFactory.initialize(requireContext())

        binding = FragmentYandexMapBinding.inflate(layoutInflater)

        mapView = binding?.mapview

        mapView?.map?.isRotateGesturesEnabled = false

        mapView?.map?.addTapListener(this)
        mapView?.map?.addInputListener(this)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentLocation()
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        mapView?.map?.addCameraListener(this)
        setSearchOperation()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {

    }

    override fun onObjectRemoved(p0: UserLocationView) {
        println("removed")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
        println("updated")
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {

    }

    override fun onSearchResponse(response: Response) {
        val mapObjects = mapView?.map?.mapObjects
        mapObjects?.clear()

        for (searchResult in response.collection.children) {
            val obj = searchResult.obj
            val resultLocation = obj?.geometry?.get(0)?.point
            val toponymObjectMetadata = obj?.metadataContainer?.getItem(ToponymObjectMetadata::class.java)

            if (resultLocation != null) {
                mapObjects?.addPlacemark(
                    resultLocation,
                    ImageProvider.fromResource(requireContext(), R.drawable.ic_action_name)
                )
                mapView?.map?.move(
                    CameraPosition(resultLocation, 14f, 0f, 0f),
                    Animation(Animation.Type.SMOOTH, 0f),
                    null
                )
            }

            getBottomDialog(
                obj?.name,
                obj?.geometry?.get(0),
                obj?.descriptionText, toponymObjectMetadata?.address
            ).show()
        }
    }

    override fun onSearchError(p0: Error) {
        toast("Error!")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        val geoObject = geoObjectTapEvent.geoObject
        val business = geoObject.metadataContainer.getItem(BusinessObjectMetadata::class.java)
        val selectionMetadata = geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)
        if (selectionMetadata != null) {
            mapView?.map?.selectGeoObject(selectionMetadata.id, selectionMetadata.layerId)
        }
        val toponymObjectMetadata = geoObject.metadataContainer
            .getItem(ToponymObjectMetadata::class.java)

        getBottomDialog(
            geoObject.name,
            geoObject.geometry[0],
            business?.workingHours.toString(), toponymObjectMetadata?.address
        ).show()
        return selectionMetadata != null
    }

    @SuppressLint("SetTextI18n")
    private fun getBottomDialog(name: String?, geometry: Geometry?, description: String?, address: Address?) =
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.bsd)
            findViewById<TextView>(R.id.name)?.text = name.toString()
            findViewById<TextView>(R.id.geometry)?.text = geometry?.point?.latitude.toString() +
                    " " + geometry?.point?.longitude.toString()
            findViewById<TextView>(R.id.description)?.text = description.toString()
            findViewById<TextView>(R.id.address)?.text = address?.formattedAddress.toString()
        }

    private fun setCurrentLocation() {
        val mapKit = MapKitFactory.getInstance()
        setUserLocation()
        mapView?.let {
            userLocationLayer = mapKit.createUserLocationLayer(it.mapWindow)
            userLocationLayer?.isVisible = true
            userLocationLayer?.isHeadingEnabled = true

            userLocationLayer?.setObjectListener(this)
            mapView?.map?.move(
                CameraPosition(
                    Point(
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0
                    ), 14f, 0f, 0f
                ),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )
        }
    }

    private fun setSearchOperation() {
        binding?.findButton?.setOnClickListener {
            val toPolygon = VisibleRegionUtils.toPolygon(mapView?.map?.visibleRegion!!)
            searchSession = searchManager?.submit(
                binding?.find?.text.toString(),
                toPolygon,
                SearchOptions().apply { geometry = true },
                this
            )
        }
    }

    private fun setUserLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location = it
        }
    }

    override fun onMapTap(p0: Map, p1: Point) {
        mapView?.map?.deselectGeoObject()
    }

    override fun onMapLongTap(p0: Map, p1: Point) {

    }
}