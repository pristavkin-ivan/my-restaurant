package com.vano.myweather.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityCitiesBinding
import com.vano.myweather.view.fragment.CitiesFragment


class CitiesActivity : AppCompatActivity(), CitiesFragment.Listener {

    private var binding: ActivityCitiesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitiesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fragment_container, CitiesFragment(this))
        transaction.commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }
}