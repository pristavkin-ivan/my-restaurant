package com.vano.myrestaurant.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.model.util.ActivityUtil;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ActivityUtil.configureActionBar(this, getString(R.string.my_orders));
    }
}