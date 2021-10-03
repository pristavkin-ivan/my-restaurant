package com.vano.myrestaurant.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.controller.activity.DrinkDetailActivity;
import com.vano.myrestaurant.model.entity.Drink;
import com.vano.myrestaurant.model.service.DrinkService;

import java.util.List;
import java.util.stream.Collectors;

public class DrinkFragment extends Fragment implements RecyclerAdapter.Listener {

    private DrinkService drinkService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recycler
                = (RecyclerView) inflater.inflate(R.layout.fragment_drink, container, false);
        drinkService = new DrinkService(getContext());

        final List<Drink> drinks = drinkService.readAllDrink();

        final List<String> names = drinks.stream().map(Drink::getName).collect(Collectors.toList());
        final List<Integer> resourceIds
                = drinks.stream().map(Drink::getResourceId).collect(Collectors.toList());

        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        recycler.setAdapter(new RecyclerAdapter(resourceIds, names, this));

        return recycler;
    }

    @Override
    public void onItemClick(int position) {
        final Intent intent = new Intent(getContext(), DrinkDetailActivity.class);

        intent.putExtra(DrinkDetailActivity.ID, position);
        startActivity(intent);
    }

}