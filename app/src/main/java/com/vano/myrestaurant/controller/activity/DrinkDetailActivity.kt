package com.vano.myrestaurant.controller.activity

import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.model.service.DrinkService
import android.os.Bundle
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.util.ActivityUtil
import com.vano.myrestaurant.model.entity.Drink
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

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

    private var drinkService: DrinkService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_detail1)

        drinkService = DrinkService(this)

        ActivityUtil.configureActionBar(this, getString(R.string.drink_title))
        val drink = drinkService?.read(extraId)

        drink?.let {  setActivityInfo(it) }
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
    private fun setActivityInfo(drink: Drink) {
        val photo = findViewById<ImageView>(R.id.photo)
        val name = findViewById<TextView>(R.id.name)
        val description = findViewById<TextView>(R.id.description)
        val volume = findViewById<TextView>(R.id.volume)

        photo.setImageResource(drink.resourceID)
        photo.contentDescription = drink.name
        name.text = drink.name
        description.text = drink.description
        volume.text = drink.volume.toString() + getString(R.string.volume_measure_unit)
    }

}