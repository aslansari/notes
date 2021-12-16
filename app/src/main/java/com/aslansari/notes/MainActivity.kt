package com.aslansari.notes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    val viewModel: NoteViewModel by viewModels(factoryProducer = {
        NoteViewModelFactory((application as NoteApp).repository)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            actionBar?.title = navController.currentDestination?.label
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.nav_host_fragment).navigateUp()
        return super.onSupportNavigateUp()
    }
}