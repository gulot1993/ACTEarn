package com.example.actearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.actearn.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        when(navController.currentDestination?.id) {
            R.id.dsaHomeFragment -> finish()
            R.id.studentHomeFragment -> finish()
            R.id.professorHomeFragment -> finish()
        }
        super.onBackPressed()
    }
}