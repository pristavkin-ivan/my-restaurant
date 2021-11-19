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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CitiesFragment @Inject constructor(private val cityFragment: CityFragment,
                                         private val recyclerAdapter: CityAdapter)
    : Fragment(), CityAdapter.Listener {

    var listener: Listener? = null

    private var cityViewModel: CityViewModel? = null

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
        cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]

        recyclerAdapter.listener = this
        cityViewModel?.getAllSavedCitiesRx()?.observe(viewLifecycleOwner) {
            recyclerAdapter.cities = it
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = recyclerAdapter
    }

    override fun onClick(city: City?) {
        cityFragment.city = city
        listener?.replaceFragment(cityFragment)
    }

    interface Listener {
        fun replaceFragment(fragment: Fragment)
    }
}