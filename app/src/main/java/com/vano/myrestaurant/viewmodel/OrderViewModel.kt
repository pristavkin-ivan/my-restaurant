package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.db.MyRestaurantDatabase
import com.vano.myrestaurant.entity.Order
import com.vano.myrestaurant.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class OrderViewModel(application: Application): AndroidViewModel(application) {

    private val orderRepository: OrderRepository

    val readAll: LiveData<List<Order>>

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        orderRepository = OrderRepository(myRestaurantDatabase.orderDao())
        readAll = orderRepository.readAll
    }

    fun createOrder(foodId: Int, drinkId: Int): Int {
        viewModelScope.launch (Dispatchers.IO) {
            orderRepository.createOrder(Order(0, foodId + 1, drinkId + 1))
        }
        return 1
    }

    fun deleteOrder(orderId: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            //orderDao.delete(orderId)
        }
    }

}