package com.vano.myrestaurant.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.controller.fragment.FoodFragment;
import com.vano.myrestaurant.controller.fragment.RecyclerAdapter;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.service.FoodService;
import com.vano.myrestaurant.model.util.ActivityUtil;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteFoodActivity extends AppCompatActivity implements FoodFragment.Listener {

    private final FoodService foodService;

    public FavoriteFoodActivity() {
        foodService = new FoodService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_food);

        ActivityUtil.configureActionBar(this, getString(R.string.favorite_title));
        insertFragment();
    }

    private void insertFragment() {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, new FoodFragment(this));
        //fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void configureView(RecyclerView recyclerView) {
        List<Food> food = foodService.readFavoriteFood();

        final List<String> names = food.stream().map(Food::getName).collect(Collectors.toList());
        final List<Integer> resourceIds = food.stream().map(Food::getResourceId).collect(Collectors.toList());

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));

        recyclerView.setAdapter(new RecyclerAdapter(resourceIds, names, null));
    }
}