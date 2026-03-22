package com.example.tasques.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "tasca_tag",
    primaryKeys = ["tascaId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = Tasca::class,
            parentColumns = ["id"],
            childColumns = ["tascaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("tascaId"),
        Index("tagId")
    ]
)
data class TascaTag(
    val tascaId: Int,
    val tagId: Int
)
