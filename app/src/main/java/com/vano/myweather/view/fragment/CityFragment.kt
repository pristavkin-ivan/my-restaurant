package com.vano.myweather.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vano.myrestaurant.R
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
            fillCity(it)
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
            getBottomSheet(cityViewModel).show()
        }
    }

    private fun getBottomSheet(cityViewModel: CityViewModel) =
        BottomSheetDialog(requireContext()).apply {

            setContentView(R.layout.bottom_sheet_dialog)
            findViewById<Button>(R.id.yes)?.setOnClickListener { _ ->
                city?.let {
                    cityViewModel.getCityRx(it.name).observe(viewLifecycleOwner) { c ->
                        fillCity(c)
                        cityViewModel.updateCityInDb(c)
                        fillRecent(city)
                        city = c
                    }
                }
                cancel()
            }
            findViewById<Button>(R.id.no)?.setOnClickListener {
                cancel()
            }
        }

    private fun fillCity(city: City?) {
        binding?.cityName?.text = city?.name
        binding?.temp?.text = city?.temperature.toString()
        binding?.descr?.text = city?.description
        binding?.hum?.text = city?.humidity.toString()
        binding?.feels?.text = city?.feelsLikeTemperature.toString()
    }

    private fun fillRecent(city: City?) {
        binding?.tempOld?.text = city?.temperature.toString()
        binding?.descrOld?.text = city?.description
        binding?.humOld?.text = city?.humidity.toString()
        binding?.feelsOld?.text = city?.feelsLikeTemperature.toString()
    }

}