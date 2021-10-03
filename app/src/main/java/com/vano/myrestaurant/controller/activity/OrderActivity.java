package com.vano.myrestaurant.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.vano.myrestaurant.R;
import com.vano.myrestaurant.model.RestaurantDatabaseHelper;
import com.vano.myrestaurant.model.dao.DrinkDao;
import com.vano.myrestaurant.model.dao.FoodDao;
import com.vano.myrestaurant.model.entity.Drink;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.service.OrderService;
import com.vano.myrestaurant.model.util.ActivityUtil;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private OrderService orderService;

    private int orderId = 0;

    public OrderActivity() {
        this.orderService = new OrderService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActivityUtil.configureActionBar(this, getString(R.string.order_title));
        populateDrinksSpinner();
        populateFoodSpinner();

        orderService.readAllOrders();
        configureFAB();
    }

    private void configureFAB() {
        FloatingActionButton floatingButton = findViewById(R.id.submit);
        floatingButton.setOnClickListener(button -> {
            final Spinner foodSpinner = findViewById(R.id.food_spinner);
            final Spinner drinkSpinner = findViewById(R.id.drink_spinner);
            orderId = orderService.createOrder(foodSpinner.getSelectedItem().toString()
                    , drinkSpinner.getSelectedItem().toString());
            configureSnackBar();
        });
    }

    private void configureSnackBar() {
        final Snackbar snackbar
                = Snackbar.make(findViewById(R.id.coordinator), R.string.order_created, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, view -> orderService.deleteOrder(orderId));

        snackbar.show();
    }

    private void populateDrinksSpinner() {
        final List<Drink> drinks = new DrinkDao(new RestaurantDatabaseHelper(this)).findAll();
        final ArrayAdapter<Drink> adapter = new ArrayAdapter<>(this
                , android.R.layout.simple_list_item_1
                , drinks);

        Spinner spinner = findViewById(R.id.drink_spinner);
        spinner.setAdapter(adapter);
    }

    private void populateFoodSpinner() {
        final List<Food> food = new FoodDao(new RestaurantDatabaseHelper(this)).findAll();
        final ArrayAdapter<Food> adapter = new ArrayAdapter<>(this
                , android.R.layout.simple_list_item_1
                , food);

        Spinner spinner = findViewById(R.id.food_spinner);
        spinner.setAdapter(adapter);
    }

}