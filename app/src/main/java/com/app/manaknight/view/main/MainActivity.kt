package com.app.manaknight.view.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.app.manaknight.R
import com.app.manaknight.databinding.ActivityMainBinding
import com.app.manaknight.util.isLoggedIn
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var t: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        t = ActionBarDrawerToggle(this, binding.root, R.string.Open, R.string.Close)
        binding.root.addDrawerListener(t!!)
        t!!.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        AppBarConfiguration(navController.graph, binding.drawerLayout)

        binding.nv.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settings -> navController.navigate(R.id.resetPasswordFragment)
                else -> return@OnNavigationItemSelectedListener true
            }
            true
        })


        if (isLoggedIn()) navController.navigate(R.id.homeFragment)
        else navController.navigate(R.id.loginFragment)

    }

    override fun onBackPressed() {
        when (navController?.currentDestination?.id) {
            R.id.loginFragment -> this.moveTaskToBack(true)
            else -> super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (t!!.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }
}