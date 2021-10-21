package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.entity.Food
import com.vano.myrestaurant.model.dao.FoodDao
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FoodService(context: Context) {

    private val foodDao: FoodDao

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(context)
        foodDao = myRestaurantDatabase.foodDao()
    }

    fun readAllFood(): List<Food> {
        var list: List<Food>? = null
        var j = GlobalScope.launch(Dispatchers.IO) {
            list = foodDao.findAll()
        }

        Thread.sleep(100)
        return list ?: Collections.emptyList()
    }

    fun readFavoriteFood(): List<Food> {
        var list: List<Food>? = null
        var j = GlobalScope.launch(Dispatchers.IO) {
            list = foodDao.findAllFavorite()
        }

        Thread.sleep(100)
        return list ?: Collections.emptyList()
    }

    fun read(id: Int): Food? {
        var food: Food? = null
        var j = GlobalScope.launch(Dispatchers.IO) {
            food = foodDao.find(id + 1)
        }

        Thread.sleep(100)
        return food
    }

    fun addToFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            foodDao.updateFavoriteColumn(id + 1, true)
        }
    }

    fun deleteFromFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            foodDao.updateFavoriteColumn(id + 1, false)
        }
    }

    /*fun insertFood() {
        GlobalScope.launch(Dispatchers.IO) {
            foodDao.add(
                Food(
                    0,
                    "Roast potato",
                    "Delicious roast potato with ham and herbs",
                    4500,
                    R.drawable.potato
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Hamburger",
                    "American burger with beef, cheese and vegetables",
                    300,
                    R.drawable.hamburger
                )
            )
            foodDao.add(
                Food(
                    0, "Borsch", "Traditional russian soup with beet", 350, R.drawable.borsch
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Dumplings",
                    "Delicious dumplings with pork and sour cream",
                    400,
                    R.drawable.pelmeni
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Caesar salad",
                    "Diet salad with chicken and vegetables",
                    300,
                    R.drawable.caesar
                )
            )
        }
    }*/
}