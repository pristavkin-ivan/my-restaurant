package com.vano.myweather.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vano.myrestaurant.databinding.ActivityCitiesBinding


class CitiesActivity : AppCompatActivity() {

    private var binding: ActivityCitiesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitiesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}