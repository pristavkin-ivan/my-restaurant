package com.vano.myrestaurant.model.service;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.vano.myrestaurant.model.RestaurantDatabaseHelper;
import com.vano.myrestaurant.model.dao.Dao;
import com.vano.myrestaurant.model.dao.FoodDao;
import com.vano.myrestaurant.model.entity.Food;

import java.util.List;

public class FoodService {

    private final Dao<Food> foodDao;

    public FoodService(Context context) {
        SQLiteOpenHelper dbHelper = new RestaurantDatabaseHelper(context);
        foodDao = new FoodDao(dbHelper);
    }

    public List<Food> readAllFood() {
        return foodDao.findAll();
    }

    public List<Food> readFavoriteFood() {
        return foodDao.findAllFavorite();
    }

    public Food read(int id) {
        return foodDao.findById(id);
    }

    public void addToFavorite(int id) {
        foodDao.updateFavoriteColumn(id, 1);
    }

    public void deleteFromFavorite(int id) {
        foodDao.updateFavoriteColumn(id, 0);
    }
}
