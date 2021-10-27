package com.vano.myrestaurant.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vano.myrestaurant.model.entity.Food

@Dao
interface FoodDao {

    @Query("select * from Food")
    fun findAll(): LiveData<List<Food>>

    @Query("select * from Food where favorite = 1")
    fun findAllFavorite(): LiveData<List<Food>>

    @Query("select * from Food where id = :id")
    fun find(id: Int): LiveData<Food>

    @Query("update Food set favorite = :flag where id = :id")
    suspend fun updateFavoriteColumn(id: Int, flag: Boolean)

    @Insert
    suspend fun add(food: Food)
}