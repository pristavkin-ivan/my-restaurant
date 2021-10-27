package com.vano.myrestaurant.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.view.activity.DrinkDetailActivity
import com.vano.myrestaurant.model.adapter.RecyclerAdapter
import com.vano.myrestaurant.model.entity.Card
import com.vano.myrestaurant.viewmodel.DrinkViewModel

class DrinkFragment : Fragment(), RecyclerAdapter.Listener {

    private companion object {
        const val SPAN_AMOUNT = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recycler = inflater.inflate(R.layout.fragment_drink, container, false) as RecyclerView
        configureRecycler(recycler)

        return recycler
    }

    private fun configureRecycler(recycler: RecyclerView): RecyclerView {
        val drinkViewModel = ViewModelProvider(this)[DrinkViewModel::class.java]

        recycler.layoutManager = StaggeredGridLayoutManager(SPAN_AMOUNT, RecyclerView.VERTICAL)

        val recyclerAdapter = RecyclerAdapter()

        drinkViewModel.readAll().observe(viewLifecycleOwner) {
            recyclerAdapter.cards = it.map { drink -> Card(drink.resourceID, drink.name, drink.id) }
        }
        recyclerAdapter.listener = this
        recycler.adapter = recyclerAdapter

        return recycler
    }

    override fun onItemClick(id: Int, bundle: Bundle?) {
        val intent = Intent(activityContext, DrinkDetailActivity::class.java)

        intent.putExtra(DrinkDetailActivity.ID, id)
        startActivity(intent, bundle)
    }

    override val activityContext: Activity?
        get() = activity

}