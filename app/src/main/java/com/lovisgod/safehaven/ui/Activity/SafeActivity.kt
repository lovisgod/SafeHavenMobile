package com.lovisgod.safehaven.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.lovisgod.safehaven.R
import com.pixplicity.easyprefs.library.Prefs

class SafeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe)
        drawerLayout= findViewById(R.id.drawer_layout)

        var navigationView: NavigationView = findViewById(R.id.business_nav_view)
        var hView = navigationView.getHeaderView(0)
        navigationView.itemIconTintList = null
        navController = Navigation.findNavController(this, R.id.app_nav_host_fragment)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {

            }
        }

        var menuIcon = findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        var logout = findViewById<TextView>(R.id.logout)
        logout.setOnClickListener {
                Prefs.putString("token", "")
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
        }

        /*
     *  handle navigation header clicked
     * */
        hView.setOnClickListener {
            Toast.makeText(this, "this is working", Toast.LENGTH_LONG).show()
            drawerLayout.closeDrawers()
//            navController.navigate(R.id.profileFragment)
        }
    }

    // handles navigation click listener

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        drawerLayout.closeDrawers()
        val id = item.itemId
        if (id == R.id.nav_report_history) {
          Toast.makeText(this, "history clicked", Toast.LENGTH_LONG).show()
        }
        return true
    }
}
