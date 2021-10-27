package com.vano.myrestaurant.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.vano.myrestaurant.model.util.ActivityUtil
import androidx.appcompat.app.AppCompatActivity
import com.vano.myrestaurant.view.fragment.FoodFragment
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.vano.myrestaurant.model.adapter.RecyclerAdapter
import com.vano.myrestaurant.model.entity.Card
import com.vano.myrestaurant.viewmodel.FoodViewModel

class FavoriteFoodActivity : AppCompatActivity(), FoodFragment.Listener, RecyclerAdapter.Listener {

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
        val recyclerAdapter = RecyclerAdapter(this)

        recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, 2)
        foodViewModel?.readAllFavorite()?.observe(this) {
            recyclerAdapter.cards = it.map { food -> Card(food.resourceId, food.name, food.id) }
        }
        recyclerView?.adapter = recyclerAdapter
    }

    override fun onItemClick(id: Int, bundle: Bundle?) {
        val intent = Intent(this, FoodDetailActivity::class.java)

        intent.putExtra(FoodFragment.ID, id)
        startActivity(intent, bundle)
    }

    override val activityContext: Activity
        get() = this
}