package com.vano.myrestaurant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vano.myrestaurant.entity.Drink

@Dao
interface DrinkDao {

    @Query("select * from Drink")
    fun findAll(): LiveData<List<Drink>>

    @Query("select * from Drink where id = :id")
    fun find(id: Int): LiveData<Drink>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(drink: Drink)
}