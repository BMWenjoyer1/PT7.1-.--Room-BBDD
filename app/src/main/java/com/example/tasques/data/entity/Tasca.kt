package com.example.tasques.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasques")
data class Tasca(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titol: String,
    val descripcio: String = "",
    val estat: String = "pendent", // pendent, en_proces, completada
    val dataCreacio: Long = System.currentTimeMillis(),
    val dataCanvi: Long? = null
)
