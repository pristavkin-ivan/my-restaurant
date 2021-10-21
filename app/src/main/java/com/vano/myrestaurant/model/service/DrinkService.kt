package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.model.entity.Drink
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.dao.DrinkDao
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class DrinkService(context: Context) {
    private val drinkDao: DrinkDao

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(context)
        drinkDao = myRestaurantDatabase.drinkDao()
    }

    fun readAllDrink(): List<Drink> {
        var list: List<Drink>? = null
        GlobalScope.launch (Dispatchers.IO) {
            list = drinkDao.findAll()
        }
        Thread.sleep(100)
        return list ?: Collections.emptyList()
    }

    fun read(id: Int): Drink? {
        var drink: Drink? = null
        GlobalScope.launch (Dispatchers.IO) {
            drink = drinkDao.find(id + 1)
        }
        Thread.sleep(100)
        return drink
    }

    /*fun insertDrinks() {
        GlobalScope.launch(Dispatchers.IO) {
            drinkDao.add(
                Drink(
                    0,
                    "Coca cola",
                    "The most popular soda drink all over the world!",
                    500,
                    R.drawable.cola
                )
            )
            drinkDao.add(
                Drink(
                    0, "Black tea", "Hot delicious Indian black tea!", 300, R.drawable.black_tea
                )
            )
            drinkDao.add(
                Drink(
                    0, "Green tea", "Hot delicious Indian green tea!", 300, R.drawable.green_tea
                )
            )
        }
    }*/
}