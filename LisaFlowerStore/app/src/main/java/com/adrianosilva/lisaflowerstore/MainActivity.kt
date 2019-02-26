package com.adrianosilva.lisaflowerstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.adrianosilva.lisaflowerstore.databinding.ActivityMainBinding

/**
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // data binding layout
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        // setting up Navigation
        val navController = this.findNavController(R.id.my_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        // prevent nav gesture if not on start destination
        /*navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestination || destination.id == R.id.scheduleFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.my_nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
