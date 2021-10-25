package com.vano.myrestaurant.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import com.vano.myrestaurant.viewmodel.OrderViewModel

class OrdersFragment : ListFragment() {

    private var orderViewModel: OrderViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderViewModel?.readAll?.observe(viewLifecycleOwner) {
            listAdapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1, it
            )
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}