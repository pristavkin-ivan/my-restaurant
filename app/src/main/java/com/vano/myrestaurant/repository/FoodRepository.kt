package com.vano.myrestaurant.repository

import com.vano.myrestaurant.dao.FoodDao

class FoodRepository(private val foodDao: FoodDao) {

    val readAll = foodDao.findAll()

    val readAllFavorite = foodDao.findAllFavorite()

    fun read(id: Int) = foodDao.find(id)

    suspend fun addToFavorite(id: Int) = foodDao.updateFavoriteColumn(id, true)

    suspend fun deleteFromFavorite(id: Int) = foodDao.updateFavoriteColumn(id, false)

    /*fun insertFood() {
        GlobalScope.launch(Dispatchers.IO) {
            foodDao.add(
                Food(
                    0,
                    "Roast potato",
                    "Delicious roast potato with ham and herbs",
                    4500,
                    R.drawable.potato
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Hamburger",
                    "American burger with beef, cheese and vegetables",
                    300,
                    R.drawable.hamburger
                )
            )
            foodDao.add(
                Food(
                    0, "Borsch", "Traditional russian soup with beet", 350, R.drawable.borsch
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Dumplings",
                    "Delicious dumplings with pork and sour cream",
                    400,
                    R.drawable.pelmeni
                )
            )
            foodDao.add(
                Food(
                    0,
                    "Caesar salad",
                    "Diet salad with chicken and vegetables",
                    300,
                    R.drawable.caesar
                )
            )
        }
    }*/
}