package com.vano.map.google.entity

import com.google.android.gms.maps.model.LatLng

class MyPlace(
    val name: String = "New marker",
    val geometry: LatLng = LatLng(0.0, 0.0),
    val description: String = "About",
    val address: String = "adr"
) {
}