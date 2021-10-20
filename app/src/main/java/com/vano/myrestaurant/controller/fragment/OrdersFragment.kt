package com.vano.myrestaurant.controller.fragment

import com.vano.myrestaurant.model.service.OrderService
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class OrdersFragment : ListFragment() {

    private var orderService: OrderService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderService = OrderService(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1, orderService?.readAllOrders().orEmpty()
            )
        }

        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}