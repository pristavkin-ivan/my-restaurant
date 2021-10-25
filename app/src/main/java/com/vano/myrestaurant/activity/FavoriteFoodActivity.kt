package com.vano.myrestaurant.activity

import com.vano.myrestaurant.util.ActivityUtil
import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.fragment.FoodFragment
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.vano.myrestaurant.adapter.RecyclerAdapter
import com.vano.myrestaurant.entity.Card
import com.vano.myrestaurant.viewmodel.FoodViewModel

class FavoriteFoodActivity : AppCompatActivity(), FoodFragment.Listener {

    private var foodViewModel: FoodViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_food)

        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        ActivityUtil.configureActionBar(this, getString(R.string.favorite_title))
        insertFragment()
    }

    private fun insertFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.fragment_container, FoodFragment(this))

        fragmentTransaction.commit()
    }

    override fun configureRecyclerView(recyclerView: RecyclerView?) {
        val recyclerAdapter = RecyclerAdapter()

        recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, 2)
        foodViewModel?.readAllFavorite?.observe(this) {
            recyclerAdapter.cards = it.map { food -> Card(food.resourceId, food.name) }
        }
        recyclerView?.adapter = recyclerAdapter
    }
}