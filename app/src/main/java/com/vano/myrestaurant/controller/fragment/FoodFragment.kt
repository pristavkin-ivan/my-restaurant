package com.vano.myrestaurant.controller.fragment

import android.app.ActivityOptions
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
import java.util.stream.Collectors

class FoodFragment(private var listener: Listener? = null) : Fragment(), RecyclerAdapter.Listener {

    companion object {
        private const val ID = "id"
    }

    private lateinit var foodService: FoodService

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
            val food = foodService.readAllFood()
            val names = food.stream().map(Food::name).collect(Collectors.toList())
            val resourceIds = food.stream().map(Food::resourceId).collect(Collectors.toList())

            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.adapter = RecyclerAdapter(resourceIds, names, this)
        } else {
            listener!!.configureView(recyclerView)
        }
        return recyclerView
    }

    override fun onItemClick(position: Int, bundle: Bundle?) {
        val intent = Intent(context, FoodDetailActivity::class.java)

        intent.putExtra(ID, position)
        startActivity(intent, bundle)
    }

    interface Listener {
        fun configureView(recyclerView: RecyclerView?)
    }
}