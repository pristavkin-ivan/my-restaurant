package com.vano.myrestaurant.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vano.myrestaurant.model.entity.Order

@Dao
interface OrderDao {

    @Query("select * from `Order`")
    fun findAll(): LiveData<List<Order>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(order: Order)

}