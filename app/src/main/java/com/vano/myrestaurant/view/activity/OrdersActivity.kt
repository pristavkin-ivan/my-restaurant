package com.vano.myrestaurant.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.util.ActivityUtil

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        ActivityUtil.configureActionBar(this, getString(R.string.my_orders))
    }
}