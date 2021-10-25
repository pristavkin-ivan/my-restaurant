package com.vano.myrestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vano.myrestaurant.R
import com.vano.myrestaurant.util.ActivityUtil
import com.vano.myrestaurant.entity.Drink
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.databinding.ActivityDrinkDetail1Binding
import com.vano.myrestaurant.viewmodel.DrinkViewModel

class DrinkDetailActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
        const val DEFAULT_ID_VALUE = 0
    }

    private val extraId: Int
        get() {
            val intent = intent
            return intent.getIntExtra(ID, DEFAULT_ID_VALUE)
        }

    private var binding: ActivityDrinkDetail1Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetail1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        val drinkViewModel = ViewModelProvider(this)[DrinkViewModel::class.java]

        ActivityUtil.configureActionBar(this, getString(R.string.drink_title))
        drinkViewModel.read(extraId).observe(this) {
            setActivityInfo(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteFoodActivity::class.java)
                startActivity(intent)
            }
            R.id.create_order -> {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun setActivityInfo(drink: Drink) =
        binding?.let {
            val photo = it.photo
            val name = it.name
            val description = it.description
            val volume = it.volume

            photo.setImageResource(drink.resourceID)
            photo.contentDescription = drink.name
            name.text = drink.name
            description.text = drink.description
            volume.text = drink.volume.toString() + getString(R.string.volume_measure_unit)
        }

}