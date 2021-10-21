package com.vano.myrestaurant.model.service

import android.content.Context
import com.vano.myrestaurant.model.entity.Order
import com.vano.myrestaurant.model.dao.FoodDao
import com.vano.myrestaurant.model.dao.DrinkDao
import com.vano.myrestaurant.model.dao.OrderDao
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class OrderService(context: Context) {

    private val foodDao: FoodDao

    private val drinkDao: DrinkDao

    private val orderDao: OrderDao

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(context)
        foodDao = myRestaurantDatabase.foodDao()
        drinkDao = myRestaurantDatabase.drinkDao()
        orderDao = myRestaurantDatabase.orderDao()
    }

    fun createOrder(foodId: Int, drinkId: Int): Int {
        GlobalScope.launch (Dispatchers.IO) {
            orderDao.add(Order(0, foodId + 1, drinkId + 1))
        }
        return 1
    }

    fun readAllOrders(): List<Order> {
        var list: List<Order>? = null
        GlobalScope.launch (Dispatchers.IO) {
            list = orderDao.findAll()
        }

        Thread.sleep(100)
        return list ?: Collections.emptyList()
    }

    fun deleteOrder(orderId: Int) {
        GlobalScope.launch (Dispatchers.IO) {
            //orderDao.delete(orderId)
        }
    }
}