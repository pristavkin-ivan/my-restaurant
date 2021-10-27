package com.vano.myrestaurant.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vano.myrestaurant.R
import com.vano.myrestaurant.view.fragment.DrinkFragment
import com.vano.myrestaurant.view.fragment.FoodFragment
import com.vano.myrestaurant.view.fragment.HomeFragment
import com.vano.myrestaurant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null

    private var binding: ActivityMainBinding? = null

    private companion object {
        const val POSITION_1 = 1
        const val POSITION_2 = 2
        const val FRAGMENT_AMOUNT = 3
    }

    private val tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                POSITION_1 -> tab.text = getString(R.string.food_tab)
                POSITION_2 -> tab.text = getString(R.string.drink_tab)
                else -> tab.text = getString(R.string.home_tab)
            }
        }

    private class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = FRAGMENT_AMOUNT

        override fun createFragment(position: Int): Fragment = when (position) {
            POSITION_1 -> FoodFragment()
            POSITION_2 -> DrinkFragment()
            else -> HomeFragment()
        }

    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        toolbar = binding?.toolbar?.root
        setSupportActionBar(toolbar)

        drawerLayout = binding?.drawerLayout
        configureViewPagerAndTabs()
        configureNavigationView()
        configureDrawerToggle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteFoodActivity::class.java)
                startActivity(intent)
            }
            R.id.create_order -> {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true)
            drawerLayout?.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_orders -> {
                val intent = Intent(this, OrdersActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return false
    }

    private fun configureDrawerToggle() {
        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_string, R.string.close_string
        )
        drawerLayout?.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun configureNavigationView() {
        val navigationView = binding?.nav
        navigationView?.setNavigationItemSelectedListener(this)
    }

    private fun configureViewPagerAndTabs() {
        val viewPager2 = binding?.viewPager

        viewPager2?.adapter = PagerAdapter(this)

        val tabLayout = binding?.tab

        if (viewPager2 != null && tabLayout != null)
            TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy).attach()
    }
}