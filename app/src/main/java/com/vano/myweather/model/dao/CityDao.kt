package com.vano.myweather.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vano.myweather.model.entity.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(city: City): Long

    @Query("select * from City order by name asc")
    fun getAll(): LiveData<List<City>>

    @Query("select * from City where name = :name")
    fun get(name: String): LiveData<City>

    @Update
    suspend fun update(city: City)
}