package com.example.tasques

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tasques.data.TasquesDatabase
import com.example.tasques.data.TasquesRepository
import com.example.tasques.ui.fragment.TasquesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var database: TasquesDatabase
    private lateinit var repository: TasquesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database and repository
        database = TasquesDatabase.getDatabase(this, lifecycleScope)
        repository = TasquesRepository(
            database.tascaDao(),
            database.tagDao(),
            database.tascaTagDao()
        )

        // Show TasquesFragment by default
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TasquesFragment.newInstance(repository))
                .commit()
        }
    }
}
