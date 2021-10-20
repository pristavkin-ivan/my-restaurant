package com.vano.myrestaurant.controller.activity

import com.vano.myrestaurant.model.util.ActivityUtil
import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.controller.fragment.FoodFragment
import com.vano.myrestaurant.model.service.FoodService
import android.os.Bundle
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.vano.myrestaurant.controller.fragment.RecyclerAdapter
import com.vano.myrestaurant.model.entity.Card

class FavoriteFoodActivity : AppCompatActivity(), FoodFragment.Listener {

    private var foodService: FoodService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_food)

        foodService = FoodService(this)

        ActivityUtil.configureActionBar(this, getString(R.string.favorite_title))
        insertFragment()
    }

    private fun insertFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.fragment_container, FoodFragment(this))

        fragmentTransaction.commit()
    }

    override fun configureRecyclerView(recyclerView: RecyclerView?) {
        val food = foodService?.readFavoriteFood()
        val cards = food?.map { Card(it.resourceId, it.name) }

        recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, 2)
        cards?.let {
            recyclerView?.adapter = RecyclerAdapter(cards)
        }
    }
}