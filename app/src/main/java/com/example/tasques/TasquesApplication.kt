package com.example.tasques

import android.app.Application
import com.example.tasques.data.TasquesDatabase
import com.example.tasques.data.TasquesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TasquesApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    val database by lazy { TasquesDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        TasquesRepository(
            database.tascaDao(),
            database.tagDao(),
            database.tascaTagDao()
        )
    }
}
