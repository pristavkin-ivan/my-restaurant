package com.vano.myrestaurant.model.service;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.vano.myrestaurant.model.RestaurantDatabaseHelper;
import com.vano.myrestaurant.model.dao.Dao;
import com.vano.myrestaurant.model.dao.DrinkDao;
import com.vano.myrestaurant.model.entity.Drink;

import java.util.List;

public class DrinkService {

    private final Dao<Drink> drinkDao;

    public DrinkService(Context context) {
        SQLiteOpenHelper dbHelper = new RestaurantDatabaseHelper(context);
        drinkDao = new DrinkDao(dbHelper);
    }

    public List<Drink> readAllDrink() {
        return drinkDao.findAll();
    }

    public Drink read(int id) {
        return drinkDao.findById(id);
    }

}
