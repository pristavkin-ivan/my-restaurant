package com.vano.myrestaurant.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vano.myrestaurant.model.entity.Food

@Dao
interface FoodDao {

    @Query("select * from Food")
    suspend fun findAll(): List<Food>

    @Query("select * from Food where favorite = 1")
    suspend fun findAllFavorite(): List<Food>

    @Query("select * from Food where id = :id")
    suspend fun find(id: Int): Food

    @Query("update Food set favorite = :flag where id = :id")
    suspend fun updateFavoriteColumn(id: Int, flag: Boolean)

    @Insert
    suspend fun add(food: Food)
}