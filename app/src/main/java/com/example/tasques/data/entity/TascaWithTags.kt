package com.example.tasques.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TascaWithTags(
    @Embedded
    val tasca: Tasca,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TascaTag::class,
            parentColumn = "tascaId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag> = emptyList()
)
