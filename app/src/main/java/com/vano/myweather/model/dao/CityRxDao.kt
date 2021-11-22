package com.vano.myweather.model.dao

import androidx.room.*
import com.vano.myweather.model.entity.City
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CityRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(city: City): Long

    @Query("select * from City order by name asc")
    fun getAll(): Observable<List<City>>

    @Query("select * from City where name = :name")
    fun get(name: String): Single<City>

    @Update
    suspend fun update(city: City)
}
