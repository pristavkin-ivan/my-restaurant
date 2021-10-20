package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.model.dao.Dao
import com.vano.myrestaurant.model.entity.Food
import android.database.sqlite.SQLiteOpenHelper
import com.vano.myrestaurant.model.RestaurantDatabaseHelper
import com.vano.myrestaurant.model.dao.FoodDao

class FoodService(context: Context?) {

    private val foodDao: Dao<Food>

    init {
        val dbHelper: SQLiteOpenHelper = RestaurantDatabaseHelper(context)
        foodDao = FoodDao(dbHelper)
    }

    fun readAllFood(): List<Food> {
        return foodDao.findAll()
    }

    fun readFavoriteFood(): List<Food> {
        return foodDao.findAllFavorite()
    }

    fun read(id: Int): Food {
        return foodDao.findById(id)
    }

    fun addToFavorite(id: Int) {
        foodDao.updateFavoriteColumn(id, 1)
    }

    fun deleteFromFavorite(id: Int) {
        foodDao.updateFavoriteColumn(id, 0)
    }
}