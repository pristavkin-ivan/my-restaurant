package com.vano.myrestaurant.controller.fragment

import com.vano.myrestaurant.model.service.DrinkService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.controller.activity.DrinkDetailActivity
import com.vano.myrestaurant.model.entity.Card

class DrinkFragment : Fragment(), RecyclerAdapter.Listener {

    private var drinkService: DrinkService? = null

    private companion object {
        const val SPAN_AMOUNT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinkService = DrinkService(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recycler = inflater.inflate(R.layout.fragment_drink, container, false) as RecyclerView
        val drinks = drinkService?.readAllDrink()
        val cards = drinks?.map { Card(it.resourceID, it.name) }

        return if (cards != null)
            configureRecycler(recycler, cards)
        else recycler
    }

    private fun configureRecycler(recycler: RecyclerView, cards: List<Card>): RecyclerView {

        recycler.layoutManager = StaggeredGridLayoutManager(SPAN_AMOUNT, RecyclerView.VERTICAL)

        val recyclerAdapter = RecyclerAdapter(cards)

        recyclerAdapter.listener = this
        recycler.adapter = recyclerAdapter

        return recycler
    }

    override fun onItemClick(position: Int, bundle: Bundle?) {
        val intent = Intent(context, DrinkDetailActivity::class.java)

        intent.putExtra(DrinkDetailActivity.ID, position)
        startActivity(intent, bundle)
    }
}