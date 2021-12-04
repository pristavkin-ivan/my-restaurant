package com.vano.map.google.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.vano.map.google.entity.MyPlace
import com.vano.myrestaurant.R

class MarkerInfoWindowAdapter(private val context: Context): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.marker_info, null)
        val place = marker.tag as? MyPlace

        view.findViewById<TextView>(R.id.name).text = place?.name
        view.findViewById<TextView>(R.id.geometry).text = place?.geometry.toString()
        view.findViewById<TextView>(R.id.description).text = place?.description.toString()
        view.findViewById<TextView>(R.id.address).text = place?.address
        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

}