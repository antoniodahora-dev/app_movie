package com.a3tecnology.appmovie.presenter.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
//        FirebaseHelp.getAuth().signOut()

    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomView, navController)

//        controla a visibilidade do bottomShowView
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomView.isVisible =
                destination.id == R.id.menu_home ||
                destination.id == R.id.menu_search ||
                destination.id == R.id.menu_favorite ||
                destination.id == R.id.menu_download ||
                destination.id == R.id.menu_profile
        }
    }
}