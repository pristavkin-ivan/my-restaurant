package com.vano.myrestaurant.controller.fragment

import com.vano.myrestaurant.model.service.FoodService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.entity.Food
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.controller.activity.FoodDetailActivity
import com.vano.myrestaurant.model.entity.Card
import java.util.stream.Collectors

class FoodFragment(private var listener: Listener? = null) : Fragment(), RecyclerAdapter.Listener {

    private companion object {
        const val ID = "id"
        const val SPAN_AMOUNT = 2
    }

    private var foodService: FoodService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodService = FoodService(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val recyclerView =
            inflater.inflate(R.layout.fragment_food, container, false) as RecyclerView

        if (listener == null) {
            val food = foodService?.readAllFood()

            val cards = food?.map { Card(it.resourceId, it.name)}

            cards?.let { configureRecycler(recyclerView, cards) }
        } else {
            listener?.configureRecyclerView(recyclerView)
        }
        return recyclerView
    }

    private fun configureRecycler(
        recycler: RecyclerView, cards: List<Card>
    ): RecyclerView {

        recycler.layoutManager = GridLayoutManager(recycler.context, SPAN_AMOUNT)

        val recyclerAdapter = RecyclerAdapter(cards)

        recyclerAdapter.listener = this
        recycler.adapter = recyclerAdapter

        return recycler
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