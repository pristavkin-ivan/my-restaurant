package com.vano.myweather.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityMainWeatherBinding
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.state.CityState
import com.vano.myweather.viewmodel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class MainWeatherActivity : AppCompatActivity() {

    private var binding: ActivityMainWeatherBinding? = null

    private val compositeDisposable = CompositeDisposable()

    private val cityViewModel: CityViewModel by viewModels()

    private var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        binding?.root
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar?.root)
        configureSearchButton()
        binding?.saveButton?.setOnClickListener {
            city?.let { city -> cityViewModel.saveCityRx(city) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.weather_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cities) {
            val intent = Intent(this, CitiesActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun configureSearchButton() {
        binding?.searchButton?.setOnClickListener {
            val disposable = cityViewModel.getCityRx1(
                binding?.city?.text.toString(),
                binding?.city2?.text.toString()
            )
                ?.subscribe { foundCityState ->
                    Log.d("state", "State: ${it.javaClass.name}")
                    handleStates(foundCityState)
                }

            if (disposable != null) {
                compositeDisposable.add(disposable)
            }
        }
    }

    private fun handleStates(foundCityState: CityState?) {
        when (foundCityState) {
            is CityState.EmptyCityState -> {
            }
            is CityState.LoadingCityState -> {
            }
            is CityState.LoadedCityState -> {
                city = foundCityState.city
                Log.d(
                    "city", "${city?.name} " +
                            "- ${city?.temperature}"
                )
                fillInfo(city)
            }
            is CityState.ErrorCityState -> {
                Toast.makeText(
                    this, foundCityState.errorMessage, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun fillInfo(city: City?) {
        binding?.t1?.text = city?.temperature.toString()
        binding?.t2?.text = city?.description
        binding?.t3?.text = city?.humidity.toString()
        binding?.t4?.text = city?.feelsLikeTemperature.toString()
    }

}