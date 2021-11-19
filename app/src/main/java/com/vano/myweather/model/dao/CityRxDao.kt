package com.vano.myweather.model.dao

import android.content.Context
import androidx.room.*
import com.vano.myweather.model.database.CityWeatherDatabase
import com.vano.myweather.model.entity.City
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

@Module
@InstallIn(ViewModelComponent::class)
object CityDaoModule {

    @Provides
    fun getCityRxDao(
        @ApplicationContext context: Context
    ): CityRxDao =
        CityWeatherDatabase.getCityWeatherDatabase(context).cityRxDao()

    @Provides
    fun getCityDao(
        @ApplicationContext context: Context
    ): CityDao =
        CityWeatherDatabase.getCityWeatherDatabase(context).cityDao()
}