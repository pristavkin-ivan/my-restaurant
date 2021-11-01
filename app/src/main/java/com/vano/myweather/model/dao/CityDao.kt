package com.vano.myweather.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vano.myweather.model.entity.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(city: City)

    @Query("select * from City order by name asc")
    fun getAll(): LiveData<List<City>>

    @Query("select * from City where id = :id")
    fun get(id: Int): LiveData<City>
}