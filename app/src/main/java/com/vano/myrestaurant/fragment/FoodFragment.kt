package com.vano.myrestaurant.fragment

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
import com.vano.myrestaurant.activity.FoodDetailActivity
import com.vano.myrestaurant.adapter.RecyclerAdapter
import com.vano.myrestaurant.entity.Card
import com.vano.myrestaurant.viewmodel.FoodViewModel

class FoodFragment(private var listener: Listener? = null) : Fragment(), RecyclerAdapter.Listener {

    private companion object {
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
        foodViewModel?.readAll?.observe(viewLifecycleOwner) {
            recyclerAdapter.cards = it.map { food -> Card(food.resourceId, food.name) }
        }
        recycler.adapter = recyclerAdapter

        recycler.layoutManager = GridLayoutManager(recycler.context, SPAN_AMOUNT)
    }


    override fun onItemClick(position: Int, bundle: Bundle?) {
        val intent = Intent(context, FoodDetailActivity::class.java)

        intent.putExtra(ID, position)
        startActivity(intent, bundle)
    }

    interface Listener {
        fun configureRecyclerView(recyclerView: RecyclerView?)
    }
}