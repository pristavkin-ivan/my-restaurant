package com.vano.myrestaurant.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vano.myrestaurant.model.entity.Order;
import com.vano.myrestaurant.model.service.OrderService;

public class OrdersFragment extends ListFragment {

    private OrderService orderService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        orderService = new OrderService(getContext());

        final ArrayAdapter<Order> adapter = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_list_item_1
                , orderService.readAllOrders());

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}