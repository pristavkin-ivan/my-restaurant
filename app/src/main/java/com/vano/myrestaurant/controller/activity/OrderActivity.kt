@file:JvmName("OrderActivity")

package com.vano.myrestaurant.controller.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityOrderBinding
import com.vano.myrestaurant.model.service.DrinkService
import com.vano.myrestaurant.model.service.FoodService
import com.vano.myrestaurant.model.service.OrderService
import com.vano.myrestaurant.model.util.ActivityUtil

class OrderActivity : AppCompatActivity() {

    private var orderService: OrderService? = null

    private var orderId = 0

    private var binding: ActivityOrderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        orderService = OrderService(this)

        ActivityUtil.configureActionBar(this, getString(R.string.order_title))
        populateDrinksSpinner()
        populateFoodSpinner()

        configureFAB()
    }

    private fun configureFAB() {
        val floatingButton = findViewById<FloatingActionButton>(R.id.submit)

        floatingButton.setOnClickListener {
            binding?. let {
                val foodSpinner = it.drinkSpinner
                val drinkSpinner = it.foodSpinner

                orderId = orderService?.createOrder(
                    foodSpinner.selectedItemPosition, drinkSpinner.selectedItemPosition
                ) ?: 0
                configureSnackBar()
            }
        }
    }

    private fun configureSnackBar() {
        val snackbar = binding?.let {
            Snackbar.make(
                it.coordinator,
                R.string.order_created,
                Snackbar.LENGTH_LONG
            )
        }
        snackbar?.setAction(R.string.undo) { orderService?.deleteOrder(orderId) }

        snackbar?.show()
    }

    private fun populateDrinksSpinner() {
        val drinks = DrinkService(this).readAllDrink()
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, drinks
        )
        val spinner = binding?.drinkSpinner

        spinner?.adapter = adapter
    }

    private fun populateFoodSpinner() {
        val food = FoodService(this).readAllFood()
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, food
        )
        val spinner = binding?.foodSpinner

        spinner?.adapter = adapter
    }
}