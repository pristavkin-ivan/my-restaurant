package com.vano.myweather.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityMainWeatherBinding
import com.vano.myweather.model.entity.City
import com.vano.myweather.viewmodel.CityViewModel

class MainWeatherActivity : AppCompatActivity() {

    companion object {
        const val BAD_REQUEST = "Bad request"
    }

    private var binding: ActivityMainWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        binding?.root
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar?.root)
        configureSearchButton()
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

    private fun configureSearchButton() {
        val cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]
        binding?.searchButton?.setOnClickListener {
            cityViewModel.getCity(binding?.city?.text.toString()).observe(this) { response ->
                if (response.isSuccessful) {
                    response.body()?.let { city ->
                        fillInfo(city)
                    }
                } else {
                    Toast.makeText(this, BAD_REQUEST, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun fillInfo(city: City) {
        binding?.t1?.text = city.temperature.toString()
        binding?.t2?.text = city.description
        binding?.t3?.text = city.humidity.toString()
        binding?.t4?.text = city.feelsLikeTemperature.toString()
    }
}