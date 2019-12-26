package com.zubair.kotlinjetpack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.zubair.kotlinjetpack.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private fun setUpBackButtonOnTop(){
        navController = Navigation.findNavController(this, R.id.fragment_navigation_container_view)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBackButtonOnTop()
    }


}
