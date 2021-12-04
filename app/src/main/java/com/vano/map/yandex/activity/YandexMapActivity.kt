package com.vano.map.yandex.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        insertFragment(YandexMapFragment())
    }

    private fun insertFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.map_container, fragment)
        transaction.commit()
    }

}