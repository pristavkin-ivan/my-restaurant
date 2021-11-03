package com.vano.myweather.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.FragmentCityBinding
import com.vano.myweather.model.entity.City

class CityFragment(var city: City? = null) : Fragment() {

    private var binding: FragmentCityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(layoutInflater)

        city?.let {
            fillCity()
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun fillCity() {
        binding?.cityName?.text = city?.name.toString()
        binding?.temp?.text = city?.temperature.toString()
        binding?.descr?.text = city?.description.toString()
        binding?.hum?.text = city?.humidity.toString()
        binding?.feels?.text = city?.feelsLikeTemperature.toString()
    }

}