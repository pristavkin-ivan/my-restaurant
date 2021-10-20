package com.vano.myrestaurant.controller.fragment

import com.vano.myrestaurant.model.service.DrinkService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import com.vano.myrestaurant.model.entity.Drink
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.controller.activity.DrinkDetailActivity
import java.util.stream.Collectors

class DrinkFragment : Fragment(), RecyclerAdapter.Listener {

    private lateinit var drinkService: DrinkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinkService = DrinkService(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recycler = inflater.inflate(R.layout.fragment_drink, container, false) as RecyclerView
        val drinks = drinkService.readAllDrink()
        val names = drinks.stream().map(Drink::name).collect(Collectors.toList())
        val resourceIds = drinks.stream().map(Drink::resourceID).collect(Collectors.toList())

        recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler.adapter = RecyclerAdapter(resourceIds, names, this)

        return recycler
    }

    override fun onItemClick(position: Int, bundle: Bundle?) {
        val intent = Intent(context, DrinkDetailActivity::class.java)

        intent.putExtra(DrinkDetailActivity.ID, position)
        startActivity(intent, bundle)
    }
}