@file:JvmName("OrderActivity")
package com.vano.myrestaurant.controller.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.RestaurantDatabaseHelper
import com.vano.myrestaurant.model.dao.DrinkDao
import com.vano.myrestaurant.model.dao.FoodDao
import com.vano.myrestaurant.model.service.DrinkService
import com.vano.myrestaurant.model.service.FoodService
import com.vano.myrestaurant.model.service.OrderService
import com.vano.myrestaurant.model.util.ActivityUtil

class OrderActivity : AppCompatActivity() {

    private var orderService: OrderService? = null

    private var orderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        orderService = OrderService(this)

        ActivityUtil.configureActionBar(this, getString(R.string.order_title))
        populateDrinksSpinner()
        populateFoodSpinner()

        configureFAB()
    }

    private fun configureFAB() {
        val floatingButton = findViewById<FloatingActionButton>(R.id.submit)

        floatingButton.setOnClickListener {
            val foodSpinner = findViewById<Spinner>(R.id.drink_spinner)
            val drinkSpinner = findViewById<Spinner>(R.id.food_spinner)
            orderId = orderService?.createOrder(
                foodSpinner.selectedItemPosition, drinkSpinner.selectedItemPosition
            ) ?: 0
            configureSnackBar()
        }
    }

    private fun configureSnackBar() {
        val snackbar = Snackbar.make(
            findViewById(R.id.coordinator),
            R.string.order_created,
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.undo) { orderService?.deleteOrder(orderId) }

        snackbar.show()
    }

    private fun populateDrinksSpinner() {
        val drinks = DrinkService(this).readAllDrink()
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, drinks
        )
        val spinner = findViewById<Spinner>(R.id.drink_spinner)

        spinner.adapter = adapter
    }

    private fun populateFoodSpinner() {
        val food = FoodService(this).readAllFood()
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, food
        )
        val spinner = findViewById<Spinner>(R.id.food_spinner)

        spinner.adapter = adapter
    }
}