@file:JvmName("MainActivity")
package com.vano.myrestaurant.controller.activity

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
import com.vano.myrestaurant.controller.fragment.DrinkFragment
import com.vano.myrestaurant.controller.fragment.FoodFragment
import com.vano.myrestaurant.controller.fragment.HomeFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null

    private val tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                1 -> tab.text = getString(R.string.food_tab)
                2 -> tab.text = getString(R.string.drink_tab)
                else -> tab.text = getString(R.string.home_tab)
            }
        }

    private class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment = when (position) {
            1 -> FoodFragment()
            2 -> DrinkFragment()
            else -> HomeFragment()
        }

    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
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
        val navigationView = findViewById<NavigationView>(R.id.nav)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun configureViewPagerAndTabs() {
        val viewPager2: ViewPager2 = findViewById(R.id.view_pager)

        viewPager2.adapter = PagerAdapter(this)

        val tabLayout: TabLayout = findViewById(R.id.tab)

        TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy).attach()
    }
}