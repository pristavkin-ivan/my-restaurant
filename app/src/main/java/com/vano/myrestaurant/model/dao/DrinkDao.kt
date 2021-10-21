package com.vano.myrestaurant.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vano.myrestaurant.model.entity.Drink

@Dao
interface DrinkDao {

    @Query("select * from Drink")
    suspend fun findAll(): List<Drink>

    @Query("select * from Drink where id = :id")
    suspend fun find(id: Int): Drink

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(drink: Drink)
}