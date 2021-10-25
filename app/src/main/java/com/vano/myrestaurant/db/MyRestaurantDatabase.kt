package com.vano.myrestaurant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vano.myrestaurant.dao.DrinkDao
import com.vano.myrestaurant.dao.FoodDao
import com.vano.myrestaurant.dao.OrderDao
import com.vano.myrestaurant.entity.Drink
import com.vano.myrestaurant.entity.Food
import com.vano.myrestaurant.entity.Order

@Database(entities = [Drink::class, Food::class, Order::class], version = 2)
abstract class MyRestaurantDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    abstract fun foodDao(): FoodDao

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var instance: MyRestaurantDatabase? = null

        fun getRestaurantDatabase(context: Context): MyRestaurantDatabase {
            val tempInstance = instance

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext, MyRestaurantDatabase::class.java, "my_restaurant"
                ).build()

                instance = inst
                return inst
            }
        }
    }
}

