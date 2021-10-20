package com.vano.myrestaurant.controller.activity

import com.vano.myrestaurant.model.util.ActivityUtil
import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.controller.fragment.FoodFragment
import com.vano.myrestaurant.model.service.FoodService
import android.os.Bundle
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.model.entity.Food
import androidx.recyclerview.widget.GridLayoutManager
import com.vano.myrestaurant.controller.fragment.RecyclerAdapter
import java.util.stream.Collectors

class FavoriteFoodActivity : AppCompatActivity(), FoodFragment.Listener {

    private val foodService: FoodService = FoodService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_food)

        ActivityUtil.configureActionBar(this, getString(R.string.favorite_title))
        insertFragment()
    }

    private fun insertFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.fragment_container, FoodFragment(this))
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
    }

    override fun configureView(recyclerView: RecyclerView?) {
        val food = foodService.readFavoriteFood()
        val names = food.stream().map(Food::name).collect(Collectors.toList())
        val resourceIds = food.stream().map(Food::resourceId).collect(Collectors.toList())

        recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, 2)
        recyclerView?.adapter = RecyclerAdapter(resourceIds, names, null)
    }
}