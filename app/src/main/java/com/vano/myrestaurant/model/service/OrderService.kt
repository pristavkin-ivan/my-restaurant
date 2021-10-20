package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.model.dao.Dao
import com.vano.myrestaurant.model.entity.Food
import com.vano.myrestaurant.model.entity.Drink
import com.vano.myrestaurant.model.entity.Order
import android.database.sqlite.SQLiteOpenHelper
import com.vano.myrestaurant.model.RestaurantDatabaseHelper
import com.vano.myrestaurant.model.dao.FoodDao
import com.vano.myrestaurant.model.dao.DrinkDao
import com.vano.myrestaurant.model.dao.OrderDao

class OrderService(context: Context?) {

    private val foodDao: Dao<Food>

    private val drinkDao: Dao<Drink>

    private val orderDao: Dao<Order>

    init {
        val dbHelper: SQLiteOpenHelper = RestaurantDatabaseHelper(context)
        foodDao = FoodDao(dbHelper)
        drinkDao = DrinkDao(dbHelper)
        orderDao = OrderDao(dbHelper)
    }

    fun createOrder(foodName: String?, drinkName: String?): Int {
        val food = foodDao.findByName(foodName)
        val drink = drinkDao.findByName(drinkName)
        return orderDao.create(Order(0, food, drink))
    }

    fun readAllOrders(): List<Order> {
        return orderDao.findAll()
    }

    fun deleteOrder(orderId: Int) {
        orderDao.delete(orderId)
    }
}