package com.vano.myweather.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import com.vano.myrestaurant.R

class CityFragment : ListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //listAdapter = ArrayAdapter<City>()
        return inflater.inflate(R.layout.fragment_city, container, false)
    }
}