package com.vano.myrestaurant.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vano.myrestaurant.R;
import com.vano.myrestaurant.controller.fragment.DrinkFragment;
import com.vano.myrestaurant.controller.fragment.FoodFragment;
import com.vano.myrestaurant.controller.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private final TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = (tab, position) -> {
        switch (position) {
            case 1: {
                tab.setText(getString(R.string.food_tab));
                break;
            }
            case 2: {
                tab.setText(getString(R.string.drink_tab));
                break;
            }
            default: {
                tab.setText(getString(R.string.home_tab));
            }
        }
    };
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        configureViewPagerAndTabs();
        configureNavigationView();
        configureDrawerToggle();
    }

    private void configureDrawerToggle() {
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                , toolbar
                , R.string.open_string
                , R.string.close_string);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite: {
                final Intent intent = new Intent(this, FavoriteFoodActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.create_order: {
                final Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureViewPagerAndTabs() {
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tab);
        final TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2
                , tabConfigurationStrategy);

        mediator.attach();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.my_orders: {
                final Intent intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return false;
    }

    private void configureNavigationView() {
        final NavigationView navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private static class PagerAdapter extends FragmentStateAdapter {

        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 1: {
                    return new FoodFragment();
                }
                case 2: {
                    return new DrinkFragment();
                }
                default: {
                    return new HomeFragment();
                }
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}