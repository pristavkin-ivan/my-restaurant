package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import com.vano.myrestaurant.model.entity.Order
import com.vano.myrestaurant.model.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application): AndroidViewModel(application) {

    private val orderRepository: OrderRepository

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        orderRepository = OrderRepository(myRestaurantDatabase.orderDao())
    }

    fun createOrder(foodId: Int, drinkId: Int): Int {
        viewModelScope.launch (Dispatchers.IO) {
            orderRepository.createOrder(Order(0, foodId + 1, drinkId + 1))
        }
        return 1
    }

    fun readAll() = orderRepository.readAll

    fun deleteOrder(orderId: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            //orderDao.delete(orderId)
        }
    }

}