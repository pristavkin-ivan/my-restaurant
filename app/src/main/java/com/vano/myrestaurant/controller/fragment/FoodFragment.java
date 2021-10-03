package com.vano.myrestaurant.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.controller.activity.FoodDetailActivity;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.service.FoodService;

import java.util.List;
import java.util.stream.Collectors;

public class FoodFragment extends Fragment implements RecyclerAdapter.Listener {

    private Listener listener;

    private FoodService foodService;

    public FoodFragment() {
    }

    public FoodFragment(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recyclerView
                = (RecyclerView) inflater.inflate(R.layout.fragment_food, container, false);
        foodService = new FoodService(getContext());

        if (listener == null) {
            List<Food> food = foodService.readAllFood();

            final List<String> names = food.stream().map(Food::getName).collect(Collectors.toList());
            final List<Integer> resourceIds = food.stream().map(Food::getResourceId).collect(Collectors.toList());

            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));

            recyclerView.setAdapter(new RecyclerAdapter(resourceIds, names, this));

        } else {
            listener.configureView(recyclerView);
        }

        return recyclerView;
    }

    @Override
    public void onItemClick(int position) {
        final Intent intent = new Intent(getContext(), FoodDetailActivity.class);

        intent.putExtra(FoodDetailActivity.ID, position);
        startActivity(intent);
    }

    public interface Listener {
        void configureView(RecyclerView recyclerView);
    }

}