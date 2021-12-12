package com.vano.map.google.renderer

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.vano.map.google.entity.MyPlace
import com.vano.myrestaurant.R

class MyClusterRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MyPlace>
) : DefaultClusterRenderer<MyPlace>(context, map, clusterManager) {

    /**
     * The icon to use for each cluster item
     */
    private val markerIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context,
            R.color.primary
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_baseline_near_me_24,
            color
        )
    }

    /**
     * Method called before the cluster item (the marker) is rendered.
     * This is where marker options should be set.
     */
    override fun onBeforeClusterItemRendered(
        item: MyPlace,
        markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.name)
            .position(item.geometry)
            .icon(markerIcon)
    }

    /**
     * Method called right after the cluster item (the marker) is rendered.
     * This is where properties for the Marker object should be set.
     */
    override fun onClusterItemRendered(clusterItem: MyPlace, marker: Marker) {
        marker.tag = clusterItem
    }
}