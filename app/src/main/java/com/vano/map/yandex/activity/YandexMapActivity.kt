package com.vano.map.yandex.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.vano.map.yandex.fragment.YandexMapFragment
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityYandexMapBinding

class YandexMapActivity : AppCompatActivity() {

    companion object {
        const val TITLE = "Yandex map"
    }

    private var binding: ActivityYandexMapBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYandexMapBinding.inflate(layoutInflater)
        setSupportActionBar(binding?.toolbar?.root)
        supportActionBar?.title = TITLE

        setContentView(binding?.root)

        checkPermissions()
        insertFragment(YandexMapFragment())
    }

    private fun insertFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.map_container, fragment)
        transaction.commit()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
        }
    }

}