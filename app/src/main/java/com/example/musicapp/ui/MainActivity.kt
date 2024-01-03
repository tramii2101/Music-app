package com.example.musicapp.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.musicapp.R
import com.example.musicapp.base.BaseActivity
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
//        setCurrentFragment(HomeFragment())
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        NavigationUI.setupWithNavController(bottomNavView, navController)
    }

    override fun bindData() {

    }

    override fun handleEvent() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> setCurrentFragment(HomeFragment())
                R.id.action_explore -> setCurrentFragment(ExploreFragment())
                R.id.action_library -> setCurrentFragment(LibraryFragment())
                R.id.action_profile -> setCurrentFragment(ProfileFragment())
            }

            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }

}