package com.vano.myrestaurant.view.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityOrderBinding
import com.vano.myrestaurant.model.util.ActivityUtil
import com.vano.myrestaurant.viewmodel.DrinkViewModel
import com.vano.myrestaurant.viewmodel.FoodViewModel
import com.vano.myrestaurant.viewmodel.OrderViewModel

class OrderActivity : AppCompatActivity() {

    private var orderViewModel: OrderViewModel? = null

    private var orderId = 0

    private var binding: ActivityOrderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        ActivityUtil.configureActionBar(this, getString(R.string.order_title))
        populateDrinksSpinner()
        populateFoodSpinner()

        configureFAB()
    }

    private fun configureFAB() {
        val floatingButton = binding?.submit

        floatingButton?.setOnClickListener {
            binding?. let {
                val foodSpinner = it.foodSpinner
                val drinkSpinner = it.drinkSpinner

                orderId = orderViewModel?.createOrder(
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
        snackbar?.setAction(R.string.undo) { orderViewModel?.deleteOrder(orderId) }

        snackbar?.show()
    }

    private fun populateDrinksSpinner() {
        val drinkViewModel = ViewModelProvider(this)[DrinkViewModel::class.java]
        val spinner = binding?.drinkSpinner

        drinkViewModel.readAll().observe(this) {
            spinner?.adapter = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, it
            )
        }
    }

    private fun populateFoodSpinner() {
        val foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        val spinner = binding?.foodSpinner

        foodViewModel.readAll().observe(this) {
            spinner?.adapter = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, it
            )
        }
    }
}