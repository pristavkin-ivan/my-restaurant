package com.vano.myrestaurant.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.view.activity.FoodDetailActivity
import com.vano.myrestaurant.model.adapter.RecyclerAdapter
import com.vano.myrestaurant.model.entity.Card
import com.vano.myrestaurant.viewmodel.FoodViewModel

class FoodFragment(private var listener: Listener? = null) : Fragment(), RecyclerAdapter.Listener {

    internal companion object {
        const val ID = "id"
        const val SPAN_AMOUNT = 2
    }

    private var foodViewModel: FoodViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val recyclerView =
            inflater.inflate(R.layout.fragment_food, container, false) as RecyclerView

        if (listener == null) {
            configureRecycler(recyclerView)
        } else {
            listener?.configureRecyclerView(recyclerView)
        }
        return recyclerView
    }

    private fun configureRecycler(recycler: RecyclerView) {
        val recyclerAdapter = RecyclerAdapter()

        recyclerAdapter.listener = this
        foodViewModel?.readAll()?.observe(viewLifecycleOwner) {
            recyclerAdapter.cards = it.map { food -> Card(food.resourceId, food.name, food.id) }
        }
        recycler.adapter = recyclerAdapter

        recycler.layoutManager = GridLayoutManager(recycler.context, SPAN_AMOUNT)
    }


    override fun onItemClick(id: Int, bundle: Bundle?) {
        val intent = Intent(activityContext, FoodDetailActivity::class.java)

        intent.putExtra(ID, id)
        startActivity(intent, bundle)
    }

    override val activityContext: Activity?
        get() = activity

    interface Listener {
        fun configureRecyclerView(recyclerView: RecyclerView?)
    }
}