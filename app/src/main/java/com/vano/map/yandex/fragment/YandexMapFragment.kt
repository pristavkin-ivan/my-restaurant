package com.vano.map.yandex.fragment


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.FragmentYandexMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.render.internal.TextualImageProvider
import com.yandex.mapkit.search.*
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import org.jetbrains.anko.support.v4.toast


class YandexMapFragment : Fragment(), UserLocationObjectListener, CameraListener,
    Session.SearchListener, GeoObjectTapListener, InputListener, ClusterListener, ClusterTapListener {

    private var mapView: MapView? = null

    private var location: Location? = null

    private var userLocationLayer: UserLocationLayer? = null

    private var searchManager: SearchManager? = null

    private var binding: FragmentYandexMapBinding? = null

    private var searchSession: Session? = null

    private var mapObjects: MapObjectCollection? = null

    private var clusterCollection: ClusterizedPlacemarkCollection? = null

    companion object {
        const val MAP_KEY = "8b477d6f-7e31-4fc7-aad0-66dcbb2dd5d1"
        private val NULL_ISLAND = Point(0.0, 0.0)
        private val MINSK = Point(53.900965004969635, 27.5518616374231)
        const val ZERO_FLOAT = 0f
        const val EMPTY_STRING = ""
        const val ZOOM = 10f
        const val RADIUS = 70.0
        const val MIN_ZOOM = 15
        const val FONT_SIZE = 50
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
        mapObjects = mapView?.map?.mapObjects?.addCollection()
        clusterCollection =
            mapObjects?.addClusterizedPlacemarkCollection(this)

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

    override fun onSearchResponse(response: Response) {
        val mapObjects = mapView?.map?.mapObjects
        mapObjects?.clear()

        for (searchResult in response.collection.children) {
            val obj = searchResult.obj
            val resultLocation = obj?.geometry?.firstOrNull()?.point
            val toponymObjectMetadata =
                obj?.metadataContainer?.getItem(ToponymObjectMetadata::class.java)

            if (resultLocation != null) {
                mapObjects?.addPlacemark(
                    resultLocation,
                    ImageProvider.fromResource(requireContext(), R.drawable.ic_action_name)
                )
                mapView?.map?.move(
                    CameraPosition(resultLocation, ZOOM, ZERO_FLOAT, ZERO_FLOAT),
                    Animation(Animation.Type.SMOOTH, ZERO_FLOAT),
                    null
                )
            }

            getBottomDialog(
                obj?.name,
                obj?.geometry?.firstOrNull(),
                obj?.descriptionText, toponymObjectMetadata?.address
            ).show()
        }
    }

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        val geoObject = geoObjectTapEvent.geoObject
        val business = geoObject.metadataContainer.getItem(BusinessObjectMetadata::class.java)
        val selectionMetadata = geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)

        //addMarker(geoObject.geometry.firstOrNull()?.point)
        addClusterMarker(geoObject.geometry.firstOrNull()?.point)

        if (selectionMetadata != null) {
            mapView?.map?.selectGeoObject(selectionMetadata.id, selectionMetadata.layerId)
        }
        val toponymObjectMetadata = geoObject.metadataContainer
            .getItem(ToponymObjectMetadata::class.java)

        getBottomDialog(
            geoObject.name,
            geoObject.geometry.firstOrNull(),
            business?.workingHours.toString(), toponymObjectMetadata?.address
        ).show()
        return selectionMetadata != null
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

    override fun onSearchError(p0: Error) {
        toast("Error!")
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.map?.mapObjects?.clear()
        binding = null
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

    override fun onClusterAdded(cluster: Cluster) {
        cluster.appearance.setIcon(TextualImageProvider(cluster.size.toString(), FONT_SIZE, true))
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(p0: Cluster) = true

    private fun addMarker(point: Point?) {
        point?.let {
            val mark = mapObjects!!.addPlacemark(point)
            mark.opacity = 1f
            mark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.img))
            mark.isDraggable = true
        }
    }

    private fun addClusterMarker(point: Point?) {
        point?.let {
            clusterCollection?.addPlacemark(point, ImageProvider.fromResource(requireContext(), R.drawable.img))
            clusterCollection?.clusterPlacemarks(RADIUS, MIN_ZOOM)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getBottomDialog(
        name: String?,
        geometry: Geometry?,
        description: String?,
        address: Address?
    ) =
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
                    if (location == null) MINSK else
                        Point(
                            location!!.latitude,
                            location!!.longitude
                        ),
                    ZOOM, ZERO_FLOAT, ZERO_FLOAT
                ),
                Animation(Animation.Type.SMOOTH, ZERO_FLOAT),
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

    @SuppressLint("MissingPermission")
    private fun setUserLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location = it
        }
    }

    override fun onMapTap(p0: Map, p1: Point) {
        mapView?.map?.deselectGeoObject()
        addClusterMarker(p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {

    }
}