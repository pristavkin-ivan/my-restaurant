package com.vano.myweather.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.FragmentCityBinding
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.state.CityState
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

    private fun configureUpdateButton() {
        val cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]

        binding?.updateButton?.setOnClickListener { _ ->
            getBottomSheet(cityViewModel).show()
        }
    }

    private fun getBottomSheet(cityViewModel: CityViewModel) =
        BottomSheetDialog(requireContext()).apply {

            setContentView(R.layout.bottom_sheet_dialog)

            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

            findViewById<Button>(R.id.yes)?.setOnClickListener { _ ->

                stateMonitoring(cityViewModel, progressBar)
                city?.let {
                    cityViewModel.getCityRx(it.name)
                }
                cancel()
            }
            findViewById<Button>(R.id.no)?.setOnClickListener {
                cancel()
            }
        }

    private fun stateMonitoring(
        cityViewModel: CityViewModel,
        progressBar: ProgressBar?
    ) {
        cityViewModel.stateData.observe(viewLifecycleOwner) {
            Log.d("state", "State: ${it.javaClass.name}")
            when (it) {
                is CityState.EmptyCityState -> progressBar?.visibility = View.INVISIBLE
                is CityState.LoadingCityState -> progressBar?.visibility = View.VISIBLE
                is CityState.LoadedCityState -> {
                    progressBar?.visibility = View.INVISIBLE
                    updateAction(it, cityViewModel)
                }
                is CityState.ErrorCityState -> {
                    progressBar?.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        it.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateAction(
        it: CityState.LoadedCityState,
        cityViewModel: CityViewModel
    ) {
        fillCity(it.city)
        fillRecent(city)
        city = it.city
        cityViewModel.updateCityInDb(city)
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