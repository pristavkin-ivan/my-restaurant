package com.vano.myrestaurant.model.repository

import com.vano.myrestaurant.model.dao.OrderDao
import com.vano.myrestaurant.model.entity.Order

class OrderRepository(private val orderDao: OrderDao) {
    suspend fun createOrder(order: Order): Int {
        orderDao.add(order)
        return 1
    }

    val readAll = orderDao.findAll()

    suspend fun deleteOrder(orderId: Int) {

    }

}