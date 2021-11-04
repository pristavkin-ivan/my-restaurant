package com.vano.myweather.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.databinding.FragmentCityBinding
import com.vano.myweather.model.entity.City
import com.vano.myweather.viewmodel.CityViewModel

class CityFragment(var city: City? = null) : Fragment() {

    private var binding: FragmentCityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(layoutInflater)

        city?.let {
            fillCity(city)
        }
        configureUpdateButton()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun configureUpdateButton() {
        val cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]

        binding?.updateButton?.setOnClickListener { _ ->
            city?.let {
                cityViewModel.getCity(it.name).observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        cityViewModel.updateCityInDb(response.body())
                            .observe(viewLifecycleOwner) { city1 ->
                                if (city1 != null) fillCity(city1)
                            }
                    }
                }
            }
        }
    }

    private fun fillCity(city: City?) {
        binding?.cityName?.text = city?.name.toString()
        binding?.temp?.text = city?.temperature.toString()
        binding?.descr?.text = city?.description.toString()
        binding?.hum?.text = city?.humidity.toString()
        binding?.feels?.text = city?.feelsLikeTemperature.toString()
    }

}