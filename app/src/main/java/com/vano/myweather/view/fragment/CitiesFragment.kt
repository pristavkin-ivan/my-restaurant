package com.vano.myweather.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import com.vano.myweather.model.adapter.CityAdapter
import com.vano.myweather.model.entity.City
import com.vano.myweather.viewmodel.CityViewModel

class CitiesFragment(var listener: Listener? = null) : Fragment(), CityAdapter.Listener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_food, container, false)

        if (recyclerView is RecyclerView) {
            configureRecycler(recyclerView)
        }
        return recyclerView
    }

    private fun configureRecycler(recycler: RecyclerView) {
        val cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]
        val recyclerAdapter = CityAdapter(requireContext())

        recyclerAdapter.listener = this
        cityViewModel.getAllSavedCitiesRx().observe(viewLifecycleOwner) {
            recyclerAdapter.cities = it
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = recyclerAdapter
    }

    override fun onClick(city: City?) {
        listener?.replaceFragment(CityFragment(city))
    }

    interface Listener {
        fun replaceFragment(fragment: Fragment)
    }
}