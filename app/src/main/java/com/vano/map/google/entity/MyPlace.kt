package com.vano.map.google.entity

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyPlace(
    val name: String = "New marker",
    val geometry: LatLng = LatLng(0.0, 0.0),
    val description: String = "About",
    val address: String = "adr"
) : ClusterItem {
    override fun getPosition() =
        geometry

    override fun getTitle() =
        name

    override fun getSnippet() =
        address
}