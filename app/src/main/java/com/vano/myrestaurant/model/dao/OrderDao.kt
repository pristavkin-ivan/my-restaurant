package com.vano.myrestaurant.model.dao

import androidx.room.*
import com.vano.myrestaurant.model.entity.Order

@Dao
interface OrderDao {

    @Query("select * from `Order`")
    suspend fun findAll(): List<Order>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(order: Order)

}