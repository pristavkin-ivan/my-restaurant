package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.model.dao.Dao
import com.vano.myrestaurant.model.entity.Drink
import android.database.sqlite.SQLiteOpenHelper
import com.vano.myrestaurant.model.RestaurantDatabaseHelper
import com.vano.myrestaurant.model.dao.DrinkDao

class DrinkService(context: Context?) {
    private val drinkDao: Dao<Drink>

    init {
        val dbHelper: SQLiteOpenHelper = RestaurantDatabaseHelper(context)
        drinkDao = DrinkDao(dbHelper)
    }

    fun readAllDrink(): List<Drink> {
        return drinkDao.findAll()
    }

    fun read(id: Int): Drink {
        return drinkDao.findById(id)
    }
}