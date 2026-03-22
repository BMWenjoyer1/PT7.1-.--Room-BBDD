package com.example.roombbdd_gonzalez_adrian.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TasqueWithTags {
    @Embedded
    public Tasques tasques;
    
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = @Junction(
            value = TascaTag.class,
            parentColumn = "tascaId",
            entityColumn = "tagId"
        )
    )
    public List<Tags> tags;
}
