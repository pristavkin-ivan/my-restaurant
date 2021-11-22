package com.vano.myweather.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vano.myweather.model.dao.CityDao
import com.vano.myweather.model.dao.CityRxDao
import com.vano.myweather.model.entity.City
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [City::class], version = 4)
abstract class CityWeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun cityRxDao(): CityRxDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "city_weather_database"

    @Provides
    fun getCityDatabase(@ApplicationContext context: Context): CityWeatherDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CityWeatherDatabase::class.java,
            DB_NAME
        ).build()

    @Provides
    fun getCityDao(cityWeatherDatabase: CityWeatherDatabase): CityDao =
        cityWeatherDatabase.cityDao()

    @Provides
    fun getCityRxDao(cityWeatherDatabase: CityWeatherDatabase): CityRxDao =
        cityWeatherDatabase.cityRxDao()

}