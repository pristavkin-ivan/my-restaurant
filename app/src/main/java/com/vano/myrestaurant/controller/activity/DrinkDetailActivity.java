package com.vano.myrestaurant.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.model.entity.Drink;
import com.vano.myrestaurant.model.service.DrinkService;
import com.vano.myrestaurant.model.util.ActivityUtil;

public class DrinkDetailActivity extends AppCompatActivity {

    public static final String ID = "id";

    private final DrinkService drinkService;

    public DrinkDetailActivity() {
        drinkService = new DrinkService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        ActivityUtil.configureActionBar(this, getString(R.string.drink_title));
        final Drink drink = drinkService.read(getExtraId());

        setActivityInfo(drink);
    }

    private int getExtraId() {
        final Intent intent = getIntent();

       return intent.getIntExtra(ID, 0);
    }

    @SuppressLint("SetTextI18n")
    private void setActivityInfo(Drink drink) {
        ImageView photo = findViewById(R.id.photo);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView volume = findViewById(R.id.volume);

        photo.setImageResource(drink.getResourceId());
        photo.setContentDescription(drink.getName());
        name.setText(drink.getName());
        description.setText(drink.getDescription());
        volume.setText(drink.getVolume() + getString(R.string.volume_measure_unit));
    }
}